package com.fintech.payments.infrastructure.adapter;

import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.domain.port.RiskBureauService;
import com.fintech.payments.infrastructure.exception.RiskBureauTimeoutException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.util.UUID;
import java.util.random.RandomGenerator;

@ApplicationScoped
public class RiskBureauAdapter implements RiskBureauService {

    private static final Logger LOG = Logger.getLogger(RiskBureauAdapter.class);
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(2);
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();

    @ConfigProperty(name = "risk-bureau.base-url", defaultValue = "https://risk-bureau.fintech.internal")
    String baseUrl;

    @ConfigProperty(name = "risk-bureau.timeout-ms", defaultValue = "2000")
    long timeoutMs;

    @Override
    public Uni<RiskEvaluationResponse> evaluateRisk(PaymentRequest request) {
        LOG.infof("Evaluando riesgo para solicitud: %s - Monto: %s %s",
                request.id(), request.amount(), request.currency());

        return Uni.createFrom().item(() -> simulateRiskEvaluation(request))
                .onItem().ifNotNull().transform(response -> {
                    LOG.infof("Evaluación de riesgo completada - Aprobada: %s, TransactionId: %s",
                            response.approved(), response.transactionId());
                    return response;
                })
                .ifNull().failWith(() -> new RiskBureauTimeoutException(
                        "El buró de riesgos no respondió dentro del tiempo esperado"));
    }

    private RiskEvaluationResponse simulateRiskEvaluation(PaymentRequest request) {
        try {
            long processingTime = RANDOM.nextLong(500, 3000);
            Thread.sleep(processingTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.warn("Hilo interrumpido durante simulación de evaluación de riesgo");
        }

        boolean approved = evaluateRiskRules(request);
        String transactionId = UUID.randomUUID().toString();

        if (processingTimeExceeded()) {
            LOG.errorf("Timeout detectado en el buró de riesgos para solicitud: %s", request.id());
            throw new RiskBureauTimeoutException(
                    "El buró de riesgos excedió el tiempo máximo de respuesta");
        }

        return new RiskEvaluationResponse(approved, transactionId, approved ? null : "Regla de riesgo triggered");
    }

    private boolean evaluateRiskRules(PaymentRequest request) {
        if (request.amount() != null && request.amount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return false;
        }

        if (request.sourceAccount() != null && request.sourceAccount().isBlank()) {
            return false;
        }

        if (request.destinationAccount() != null && request.destinationAccount().isBlank()) {
            return false;
        }

        return true;
    }

    private boolean processingTimeExceeded() {
        return RANDOM.nextInt(100) < 5;
    }

    private long getProcessingTime() {
        return RANDOM.nextLong(500, 2500);
    }

    public record RiskEvaluationResponse(
            boolean approved,
            String transactionId,
            String rejectionReason
    ) {}

    @Override
    public String getServiceName() {
        return "RiskBureauAdapter";
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}