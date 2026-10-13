package com.fintech.payments;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import com.fintech.payments.infrastructure.config.ResilienceConfig;
import com.fintech.payments.application.service.PaymentService;
import com.fintech.payments.domain.port.RiskBureauService;
import com.fintech.payments.domain.port.CoreBankingService;

@QuarkusMain
public class Application {
    @Inject
    PaymentService paymentService;

    @Inject
    RiskBureauService riskBureauService;

    @Inject
    CoreBankingService coreBankingService;

    @ConfigProperty(name = "quarkus.mongodb.database")
    String mongoDatabase;

    @ConfigProperty(name = "quarkus.http.port")
    int httpPort;

    @ConfigProperty(name = "payment.resilience.circuit-breaker.failure-ratio")
    double circuitBreakerFailureRatio;

    public static void main(String... args) {
        Quarkus.run(Application.class, args);
    }

    public void run(String... args) {
        Log.info("Starting Payment Processing System");
        Log.infof("MongoDB database: %s", mongoDatabase);
        Log.infof("HTTP Server running on port: %d", httpPort);
        Log.infof("Circuit Breaker failure ratio configured: %.2f", circuitBreakerFailureRatio);

        try {
            // Validación de configuración inicial
            validateConfiguration();

            // Inicialización de componentes de resiliencia
            ResilienceConfig.initialize();

            // Verificación de conectividad con servicios externos
            verifyExternalServices();

            // Inicio del procesamiento de solicitudes
            Quarkus.waitForExit();
        } catch (Exception e) {
            Log.error("Failed to start application", e);
            Quarkus.asyncExit(1);
        }
    }

    private void validateConfiguration() {
        if (mongoDatabase == null || mongoDatabase.isBlank()) {
            throw new IllegalStateException("MongoDB database name is not configured");
        }

        if (circuitBreakerFailureRatio <= 0 || circuitBreakerFailureRatio > 1) {
            throw new IllegalStateException("Circuit breaker failure ratio must be between 0 and 1");
        }

        Log.info("All configurations validated successfully");
    }

    private void verifyExternalServices() {
        try {
            // Verificación asíncrona de servicios externos
            riskBureauService.checkConnectivity()
                .onItem().transform(connected -> {
                    if (!connected) {
                        Log.warn("Risk Bureau service connectivity check failed");
                    }
                    return connected;
                })
                .onFailure().recoverWithItem(false)
                .await().indefinitely();

            coreBankingService.checkConnectivity()
                .onItem().transform(connected -> {
                    if (!connected) {
                        Log.warn("Core Banking service connectivity check failed");
                    }
                    return connected;
                })
                .onFailure().recoverWithItem(false)
                .await().indefinitely();

            Log.info("External services connectivity verified");
        } catch (Exception e) {
            Log.error("Failed to verify external services connectivity", e);
            throw new RuntimeException("External services verification failed", e);
        }
    }

    public static class Startup {
        public static void main(String[] args) {
            Quarkus.run(Application.class, args);
        }
    }
}