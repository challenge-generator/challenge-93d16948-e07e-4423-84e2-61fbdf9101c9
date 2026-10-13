package com.fintech.payments.infrastructure.adapter;

import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.domain.port.CoreBankingService;
import com.fintech.payments.infrastructure.exception.CoreBankingException;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.util.UUID;
import java.util.random.RandomGenerator;

@ApplicationScoped
public class CoreBankingAdapter implements CoreBankingService {

    private static final Logger LOG = Logger.getLogger(CoreBankingAdapter.class);
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();
    private static final int MAX_RETRY_ATTEMPTS = 5;
    private static final Duration INITIAL_BACKOFF = Duration.ofMillis(500);

    @ConfigProperty(name = "core-banking.base-url", defaultValue = "https://core-banking.fintech.internal")
    String baseUrl;

    @ConfigProperty(name = "core-banking.max-retries", defaultValue = "5")
    int maxRetries;

    @ConfigProperty(name = "core-banking.initial-backoff-ms", defaultValue = "500")
    long initialBackoffMs;

    @Override
    public Uni<CoreBankingResponse> processPayment(PaymentRequest request) {
        LOG.infof("Procesando pago en core bancario: %s - Monto: %s %s",
                request.id(), request.amount(), request.currency());

        return executeWithAutomaticRetry(request, 1);
    }

    private Uni<CoreBankingResponse> executeWithAutomaticRetry(PaymentRequest request, int attemptNumber) {
        LOG.infof("Ejecutando intento %d de %d para solicitud: %s",
                attemptNumber, maxRetries, request.id());

        return Uni.createFrom().item(() -> simulateCoreBankingCall(request))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .onFailure().recoverWithUni(throwable -> {
                    LOG.errorf(throwable, "Error en intento %d para solicitud: %s", attemptNumber, request.id());
                    return handleFailureWithRetry(request, attemptNumber, throwable);
                });
    }

    private CoreBankingResponse simulateCoreBankingCall(PaymentRequest request) {
        int scenario = RANDOM.nextInt(100);

        if (scenario < 10) {
            LOG.warnf("Simulando error 500 del core bancario para solicitud: %s", request.id());
            throw new CoreBankingException("Error interno del servidor (500)");
        }

        if (scenario < 15) {
            LOG.warnf("Simulando error 503 del core bancario para solicitud: %s", request.id());
            throw new CoreBankingException("Servicio no disponible (503)");
        }

        if (scenario < 20) {
            LOG.warnf("Simulando error de timeout del core bancario para solicitud: %s", request.id());
            throw new CoreBankingException("Timeout en la comunicación con el core bancario");
        }

        boolean success = RANDOM.nextInt(100) < 85;
        String transactionId = UUID.randomUUID().toString();

        if (success) {
            LOG.infof("Pago procesado exitosamente en core bancario: %s, TransactionId: %s",
                    request.id(), transactionId);
            return new CoreBankingResponse(true, transactionId, null);
        } else {
            LOG.warnf("Core bancario rechazó el pago para solicitud: %s", request.id());
            return new CoreBankingResponse(false, null, "Pago rechazado por el core bancario");
        }
    }

    private Uni<CoreBankingResponse> handleFailureWithRetry(PaymentRequest request, int attemptNumber, Throwable throwable) {
        if (attemptNumber >= maxRetries) {
            LOG.errorf("Máximo de reintentos automáticos alcanzado para solicitud: %s", request.id());
            return Uni.createFrom().failure(
                    new CoreBankingException("Máximo de reintentos alcanzado: " + throwable.getMessage()));
        }

        long backoffDelay = calculateExponentialBackoff(attemptNumber);
        LOG.infof("Programando reintento automático en %d ms (intento %d)", backoffDelay, attemptNumber + 1);

        return Uni.createFrom().voidItem()
                .onItem().delayIt().by(Duration.ofMillis(backoffDelay))
                .chain(() -> executeWithAutomaticRetry(request, attemptNumber + 1));
    }

    private long calculateExponentialBackoff(int attemptNumber) {
        double exponentialDelay = initialBackoffMs * Math.pow(2, attemptNumber - 1);
        double jitter = RANDOM.nextDouble() * 0.3 * exponentialDelay;
        return (long) (exponentialDelay + jitter);
    }

    @Override
    public Uni<CoreBankingResponse> reversePayment(String transactionId) {
        LOG.infof("Iniciando reversión de pago: %s", transactionId);

        return Uni.createFrom().item(() -> {
            boolean success = RANDOM.nextInt(100) < 90;
            if (success) {
                String reversalId = UUID.randomUUID().toString();
                LOG.infof("Reversión exitosa para transacción: %s, ReversalId: %s", transactionId, reversalId);
                return new CoreBankingResponse(true, reversalId, null);
            } else {
                LOG.errorf("Reversión fallida para transacción: %s", transactionId);
                return new CoreBankingResponse(false, null, "Reversión fallida");
            }
        });
    }

    @Override
    public String getServiceName() {
        return "CoreBankingAdapter";
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    public record CoreBankingResponse(
            boolean success,
            String transactionId,
            String errorMessage
    ) {}
}