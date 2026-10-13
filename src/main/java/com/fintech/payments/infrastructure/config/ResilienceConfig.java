package com.fintech.payments.infrastructure.config;

import io.smallrye.faulttolerance.Fallback;
import io.smallrye.faulttolerance.CircuitBreaker;
import io.smallrye.faulttolerance.Retry;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ResilienceConfig {

    @ConfigProperty(name = "payment.circuit-breaker.failure-ratio", defaultValue = "0.5")
    double failureRatio;

    @ConfigProperty(name = "payment.circuit-breaker.request-volume", defaultValue = "10")
    int requestVolume;

    @ConfigProperty(name = "payment.circuit-breaker.delay", defaultValue = "30000")
    long circuitBreakerDelay;

    @ConfigProperty(name = "payment.retry.max-retries", defaultValue = "3")
    int maxRetries;

    @ConfigProperty(name = "payment.retry.delay", defaultValue = "1000")
    long retryDelay;

    @ConfigProperty(name = "payment.retry.jitter", defaultValue = "200")
    long retryJitter;

    public double getFailureRatio() {
        return failureRatio;
    }

    public int getRequestVolume() {
        return requestVolume;
    }

    public long getCircuitBreakerDelay() {
        return circuitBreakerDelay;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public long getRetryJitter() {
        return retryJitter;
    }

    public long calculateRetryDelay(int attemptNumber) {
        return retryDelay * attemptNumber + (long)(Math.random() * retryJitter);
    }

    public boolean shouldAttemptRetry(int currentAttempt) {
        return currentAttempt < maxRetries;
    }
}