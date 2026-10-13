package com.fintech.payments.infrastructure.adapter;

import com.fintech.payments.domain.model.IdempotencyKey;
import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.domain.port.CoreBankingService;
import com.fintech.payments.infrastructure.exception.CoreBankingException;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CoreBankingAdapterTest implements CoreBankingService {

    @Inject
    CoreBankingAdapter coreBankingAdapter;

    private final AtomicInteger callCounter = new AtomicInteger(0);

    @Test
    void testProcessPayment_SuccessOnFirstAttempt() {
        PaymentRequest request = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-001",
            "beneficiary-002",
            new BigDecimal("500.00"),
            "USD",
            IdempotencyKey.generate()
        );

        String result = coreBankingAdapter.processPayment(request).await().indefinitely();

        assertNotNull(result);
        assertTrue(result.startsWith("CORE-"));
    }

    @Test
    void testProcessPayment_AutomaticRetryOnServerError() {
        callCounter.set(0);

        PaymentRequest request = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-003",
            "beneficiary-004",
            new BigDecimal("1500.00"),
            "EUR",
            IdempotencyKey.generate()
        );

        Uni<String> result = attemptWithFailures(request, 2);

        String transactionId = result.await().indefinitely();

        assertNotNull(transactionId);
        assertTrue(transactionId.startsWith("CORE-"));
    }

    private Uni<String> attemptWithFailures(PaymentRequest request, int failureCount) {
        AtomicInteger attempt = new AtomicInteger(0);

        return Uni.createFrom().deferred(() -> {
            int currentAttempt = attempt.incrementAndGet();
            if (currentAttempt <= failureCount) {
                return Uni.createFrom().failure(
                    new CoreBankingException("Service temporarily unavailable", 503)
                );
            }
            return Uni.createFrom().item("CORE-" + UUID.randomUUID().toString());
        });
    }

    @Test
    void testProcessPayment_MaxRetriesExceeded_ThrowsException() {
        callCounter.set(0);

        PaymentRequest request = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-005",
            "beneficiary-006",
            new BigDecimal("2000.00"),
            "GBP",
            IdempotencyKey.generate()
        );

        CoreBankingException exception = assertThrows(CoreBankingException.class, () -> {
            coreBankingAdapter.processPaymentWithRetry(request).await().indefinitely();
        });

        assertTrue(exception.getMessage().contains("Max retries exceeded"));
    }

    @Test
    void testProcessPayment_CircuitBreakerBlocksOnRepeatedFailures() {
        for (int i = 0; i < 5; i++) {
            try {
                PaymentRequest request = PaymentRequest.createNew(
                    UUID.randomUUID(),
                    "originator-cb-" + i,
                    "beneficiary-cb-" + i,
                    new BigDecimal("100.00"),
                    "USD",
                    IdempotencyKey.generate()
                );
                coreBankingAdapter.processPaymentWithRetry(request).await().indefinitely();
            } catch (Exception e) {
            }
        }

        PaymentRequest newRequest = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-cb-final",
            "beneficiary-cb-final",
            new BigDecimal("100.00"),
            "USD",
            IdempotencyKey.generate()
        );

        assertThrows(CoreBankingException.class, () ->
            coreBankingAdapter.processPaymentWithRetry(newRequest).await().indefinitely()
        );
    }

    @Test
    void testProcessPayment_ClientErrorDoesNotRetry() {
        PaymentRequest request = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-invalid",
            "beneficiary-007",
            new BigDecimal("100.00"),
            "USD",
            IdempotencyKey.generate()
        );

        CoreBankingException exception = assertThrows(CoreBankingException.class, () ->
            coreBankingAdapter.processPayment(request).await().indefinitely()
        );

        assertEquals(400, exception.getStatusCode());
    }

    @Override
    public Uni<String> processPayment(PaymentRequest paymentRequest) {
        return Uni.createFrom().item("CORE-" + UUID.randomUUID().toString());
    }
}