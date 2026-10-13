package com.fintech.payments.infrastructure.exception;

public class RiskBureauTimeoutException extends RuntimeException {

    private static final long DEFAULT_TIMEOUT_MS = 2000L;

    private final long timeoutMs;
    private final String transactionId;

    public RiskBureauTimeoutException(String message) {
        super(message);
        this.timeoutMs = DEFAULT_TIMEOUT_MS;
        this.transactionId = null;
    }

    public RiskBureauTimeoutException(String message, long timeoutMs) {
        super(message);
        this.timeoutMs = timeoutMs;
        this.transactionId = null;
    }

    public RiskBureauTimeoutException(String message, Throwable cause) {
        super(message, cause);
        this.timeoutMs = DEFAULT_TIMEOUT_MS;
        this.transactionId = null;
    }

    public RiskBureauTimeoutException(String message, long timeoutMs, Throwable cause) {
        super(message, cause);
        this.timeoutMs = timeoutMs;
        this.transactionId = null;
    }

    public RiskBureauTimeoutException(String message, String transactionId, long timeoutMs) {
        super(message);
        this.timeoutMs = timeoutMs;
        this.transactionId = transactionId;
    }

    public RiskBureauTimeoutException(String message, String transactionId, long timeoutMs, Throwable cause) {
        super(message, cause);
        this.timeoutMs = timeoutMs;
        this.transactionId = transactionId;
    }

    public long getTimeoutMs() {
        return timeoutMs;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public boolean hasTransactionId() {
        return transactionId != null && !transactionId.isBlank();
    }

    public static RiskBureauTimeoutException forTransaction(String transactionId) {
        return new RiskBureauTimeoutException(
            "Timeout del buró de riesgos al procesar transacción: " + transactionId,
            transactionId,
            DEFAULT_TIMEOUT_MS
        );
    }
}