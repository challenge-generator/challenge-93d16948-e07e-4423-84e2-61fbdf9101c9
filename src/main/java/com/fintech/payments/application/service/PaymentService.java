package com.fintech.payments.application.service;

import com.fintech.payments.domain.model.IdempotencyKey;
import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.domain.model.PaymentStatus;
import com.fintech.payments.domain.port.CoreBankingService;
import com.fintech.payments.domain.port.PaymentRequestRepository;
import com.fintech.payments.domain.port.RiskBureauService;
import com.fintech.payments.infrastructure.exception.IdempotencyException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.UUID;

@ApplicationScoped
public class PaymentService {

    private static final Logger LOG = Logger.getLogger(PaymentService.class);
    private static final int MAX_RETRY_ATTEMPTS = 3;

    private final PaymentRequestRepository paymentRequestRepository;
    private final RiskBureauService riskBureauService;
    private final CoreBankingService coreBankingService;

    @Inject
    public PaymentService(
            PaymentRequestRepository paymentRequestRepository,
            RiskBureauService riskBureauService,
            CoreBankingService coreBankingService) {
        this.paymentRequestRepository = paymentRequestRepository;
        this.riskBureauService = riskBureauService;
        this.coreBankingService = coreBankingService;
    }

    public Uni<PaymentRequest> registerPaymentRequest(PaymentRequest request, IdempotencyKey idempotencyKey) {
        LOG.infof("Iniciando registro de solicitud de pago con idempotencyKey: %s", idempotencyKey.value());

        return paymentRequestRepository.findByIdempotencyKey(idempotencyKey)
                .onItem().ifNotNull().failWith(existing -> {
                    LOG.warnf("Solicitud duplicada detectada para idempotencyKey: %s", idempotencyKey.value());
                    return new IdempotencyException("Ya existe una solicitud con esta clave de idempotencia");
                })
                .onItem().ifNull().continueWith(() -> {
                    PaymentRequest newRequest = PaymentRequest.createNew(
                            request.amount(),
                            request.currency(),
                            request.sourceAccount(),
                            request.destinationAccount(),
                            request.description(),
                            idempotencyKey
                    );
                    return paymentRequestRepository.save(newRequest);
                })
                .chain(savedRequest -> processRiskAssessment(savedRequest));
    }

    private Uni<PaymentRequest> processRiskAssessment(PaymentRequest request) {
        LOG.infof("Procesando evaluación de riesgo para solicitud: %s", request.id());

        return riskBureauService.evaluateRisk(request)
                .onItem().transformToUni(riskResponse -> {
                    if (riskResponse.approved()) {
                        PaymentRequest updatedRequest = request.withRiskBureauTransactionId(riskResponse.transactionId());
                        LOG.infof("Evaluación de riesgo aprobada para solicitud: %s", request.id());
                        return processCoreBanking(updatedRequest);
                    } else {
                        LOG.warnf("Evaluación de riesgo rechazada para solicitud: %s - Razón: %s",
                                request.id(), riskResponse.rejectionReason());
                        return paymentRequestRepository.updateStatus(request.id(), PaymentStatus.RISK_REJECTED)
                                .replaceWith(request);
                    }
                })
                .onFailure().recoverWithUni(throwable -> {
                    LOG.errorf(throwable, "Error en evaluación de riesgo para solicitud: %s", request.id());
                    return handleRiskBureauFailure(request, throwable);
                });
    }

    private Uni<PaymentRequest> handleRiskBureauFailure(PaymentRequest request, Throwable throwable) {
        LOG.warnf("Manejando fallo del buró de riesgos para solicitud: %s - %s",
                request.id(), throwable.getMessage());
        return paymentRequestRepository.updateStatus(request.id(), PaymentStatus.RISK_ASSESSMENT_FAILED)
                .replaceWith(request);
    }

    private Uni<PaymentRequest> processCoreBanking(PaymentRequest request) {
        LOG.infof("Enviando solicitud al core bancario: %s", request.id());

        return executeWithRetry(request, 1);
    }

    private Uni<PaymentRequest> executeWithRetry(PaymentRequest request, int attemptNumber) {
        LOG.infof("Intento %d de %d para solicitud: %s", attemptNumber, MAX_RETRY_ATTEMPTS, request.id());

        return coreBankingService.processPayment(request)
                .onItem().transformToUni(response -> {
                    if (response.success()) {
                        PaymentRequest updatedRequest = request.withCoreBankingTransactionId(response.transactionId());
                        return paymentRequestRepository.updateStatus(updatedRequest.id(), PaymentStatus.COMPLETED)
                                .replaceWith(updatedRequest);
                    } else {
                        LOG.warnf("Core bancario respondió con error para solicitud: %s - %s",
                                request.id(), response.errorMessage());
                        return paymentRequestRepository.updateStatus(request.id(), PaymentStatus.FAILED)
                                .replaceWith(request);
                    }
                })
                .onFailure().recoverWithUni(throwable -> {
                    LOG.errorf(throwable, "Error en intento %d para solicitud: %s", attemptNumber, request.id());
                    return handleCoreBankingFailure(request, attemptNumber, throwable);
                });
    }

    private Uni<PaymentRequest> handleCoreBankingFailure(PaymentRequest request, int attemptNumber, Throwable throwable) {
        if (attemptNumber >= MAX_RETRY_ATTEMPTS) {
            LOG.errorf("Máximo de reintentos alcanzado para solicitud: %s", request.id());
            return paymentRequestRepository.updateStatus(request.id(), PaymentStatus.MAX_RETRIES_EXCEEDED)
                    .replaceWith(request);
        }

        PaymentRequest retryRequest = request.incrementRetryCount();
        return paymentRequestRepository.save(retryRequest)
                .chain(saved -> {
                    long delayMillis = calculateBackoffDelay(attemptNumber);
                    LOG.infof("Esperando %d ms antes del reintento %d", delayMillis, attemptNumber + 1);
                    return Uni.createFrom().voidItem()
                            .onItem().delayIt().by(delayMillis)
                            .chain(() -> executeWithRetry(saved, attemptNumber + 1));
                });
    }

    private long calculateBackoffDelay(int attemptNumber) {
        return (long) Math.pow(2, attemptNumber) * 1000L;
    }

    public Uni<PaymentRequest> getPaymentRequest(UUID id) {
        LOG.debugf("Consultando solicitud de pago: %s", id);
        return paymentRequestRepository.findById(id);
    }

    public Uni<PaymentRequest> getPaymentRequestByIdempotencyKey(IdempotencyKey idempotencyKey) {
        LOG.debugf("Consultando solicitud por clave de idempotencia: %s", idempotencyKey.value());
        return paymentRequestRepository.findByIdempotencyKey(idempotencyKey);
    }
}