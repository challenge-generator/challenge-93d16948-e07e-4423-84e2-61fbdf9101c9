package com.fintech.payments.infrastructure.exception;

public class CoreBankingException extends RuntimeException {
    private final String transactionId;
    private final int httpStatusCode;
    private final boolean retryable;

    public CoreBankingException(String message) {
        super(message);
        this.transactionId = null;
        this.httpStatusCode = 500;
        this.retryable = true;
    }

    public CoreBankingException(String message, String transactionId) {
        super(message);
        this.transactionId = transactionId;
        this.httpStatusCode = 500;
        this.retryable = true;
    }

    public CoreBankingException(String message, String transactionId, int httpStatusCode, boolean retryable) {
        super(message);
        this.transactionId = transactionId;
        this.httpStatusCode = httpStatusCode;
        this.retryable = retryable;
    }

    public CoreBankingException(String message, Throwable cause) {
        super(message, cause);
        this.transactionId = null;
        this.httpStatusCode = 500;
        this.retryable = true;
    }

    public CoreBankingException(String message, String transactionId, Throwable cause) {
        super(message, cause);
        this.transactionId = transactionId;
        this.httpStatusCode = 500;
        this.retryable = true;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public boolean isRetryable() {
        return retryable;
    }
}