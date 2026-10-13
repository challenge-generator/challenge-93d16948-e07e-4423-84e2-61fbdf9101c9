package com.fintech.payments.application.service;

import com.fintech.payments.domain.model.IdempotencyKey;
import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.domain.model.PaymentStatus;
import com.fintech.payments.domain.port.CoreBankingService;
import com.fintech.payments.domain.port.PaymentRequestRepository;
import com.fintech.payments.domain.port.RiskBureauService;
import com.fintech.payments.infrastructure.exception.CoreBankingException;
import com.fintech.payments.infrastructure.exception.IdempotencyException;
import com.fintech.payments.infrastructure.exception.RiskBureauTimeoutException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class PaymentServiceTest {

    @Inject
    PaymentService paymentService;

    @InjectMock
    PaymentRequestRepository paymentRequestRepository;

    @InjectMock
    RiskBureauService riskBureauService;

    @InjectMock
    CoreBankingService coreBankingService;

    private PaymentRequest sampleRequest;
    private IdempotencyKey sampleKey;

    @BeforeEach
    void setUp() {
        sampleKey = IdempotencyKey.generate();
        sampleRequest = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-123",
            "beneficiary-456",
            new BigDecimal("1000.00"),
            "USD",
            sampleKey
        );
    }

    @Test
    void testProcessPayment_NewRequest_CreatesSuccessfully() {
        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().nullItem());
        when(paymentRequestRepository.save(any(PaymentRequest.class)))
            .thenAnswer(inv -> Uni.createFrom().item(inv.getArgument(0)));
        when(riskBureauService.evaluateRisk(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().item("risk-token-123"));
        when(coreBankingService.processPayment(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().item("core-txn-789"));

        PaymentRequest result = paymentService.processPayment(sampleRequest)
            .await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.COMPLETED, result.status());
        assertNotNull(result.riskBureauTransactionId());
        assertNotNull(result.coreBankingTransactionId());
        verify(paymentRequestRepository).save(any(PaymentRequest.class));
    }

    @Test
    void testProcessPayment_DuplicateIdempotencyKey_ThrowsException() {
        PaymentRequest existingRequest = sampleRequest.withStatus(PaymentStatus.COMPLETED);
        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().item(existingRequest));

        assertThrows(IdempotencyException.class, () ->
            paymentService.processPayment(sampleRequest).await().indefinitely()
        );
        verify(paymentRequestRepository, never()).save(any(PaymentRequest.class));
    }

    @Test
    void testProcessPayment_RiskBureauTimeout_HandlesGracefully() {
        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().nullItem());
        when(paymentRequestRepository.save(any(PaymentRequest.class)))
            .thenAnswer(inv -> Uni.createFrom().item(inv.getArgument(0)));
        when(riskBureauService.evaluateRisk(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().failure(new RiskBureauTimeoutException("Risk bureau timeout")));

        PaymentRequest result = paymentService.processPayment(sampleRequest)
            .await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.RISK_CHECK_FAILED, result.status());
        assertNull(result.riskBureauTransactionId());
    }

    @Test
    void testProcessPayment_CoreBankingFailure_RetriesAndEventuallyFails() {
        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().nullItem());
        when(paymentRequestRepository.save(any(PaymentRequest.class)))
            .thenAnswer(inv -> Uni.createFrom().item(inv.getArgument(0)));
        when(paymentRequestRepository.updateStatus(any(UUID.class), any(PaymentStatus.class)))
            .thenAnswer(inv -> Uni.createFrom().item(true));
        when(riskBureauService.evaluateRisk(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().item("risk-token-123"));
        when(coreBankingService.processPayment(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().failure(
                new CoreBankingException("Core banking unavailable", 503)
            ));

        PaymentRequest result = paymentService.processPayment(sampleRequest)
            .onFailure().recoverWithItem(throwable -> {
                PaymentRequest recovered = sampleRequest.withStatus(PaymentStatus.FAILED);
                return recovered;
            })
            .await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.FAILED, result.status());
        verify(coreBankingService, atLeast(1)).processPayment(any(PaymentRequest.class));
    }

    @Test
    void testProcessPayment_MultipleRetriesSucceeds() {
        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().nullItem());
        when(paymentRequestRepository.save(any(PaymentRequest.class)))
            .thenAnswer(inv -> Uni.createFrom().item(inv.getArgument(0)));
        when(paymentRequestRepository.updateStatus(any(UUID.class), any(PaymentStatus.class)))
            .thenReturn(Uni.createFrom().item(true));
        when(riskBureauService.evaluateRisk(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().item("risk-token-123"));

        Mockito.when(coreBankingService.processPayment(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().failure(new CoreBankingException("Service unavailable", 503)))
            .thenReturn(Uni.createFrom().failure(new CoreBankingException("Service unavailable", 503)))
            .thenReturn(Uni.createFrom().item("core-success-789"));

        PaymentRequest result = paymentService.processPayment(sampleRequest)
            .await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.COMPLETED, result.status());
        assertEquals("core-success-789", result.coreBankingTransactionId());
    }

    @Test
    void testProcessPayment_InvalidAmount_ValidationFails() {
        PaymentRequest invalidRequest = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-123",
            "beneficiary-456",
            new BigDecimal("-100.00"),
            "USD",
            IdempotencyKey.generate()
        );

        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().nullItem());

        assertThrows(IllegalArgumentException.class, () ->
            paymentService.processPayment(invalidRequest).await().indefinitely()
        );
    }

    @Test
    void testProcessPayment_EmptyOriginator_ValidationFails() {
        PaymentRequest invalidRequest = PaymentRequest.createNew(
            UUID.randomUUID(),
            "",
            "beneficiary-456",
            new BigDecimal("1000.00"),
            "USD",
            IdempotencyKey.generate()
        );

        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().nullItem());

        assertThrows(IllegalArgumentException.class, () ->
            paymentService.processPayment(invalidRequest).await().indefinitely()
        );
    }
}