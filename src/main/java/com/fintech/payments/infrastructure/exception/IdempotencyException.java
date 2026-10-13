package com.fintech.payments.infrastructure.exception;

public class IdempotencyException extends RuntimeException {

    private final String idempotencyKey;

    public IdempotencyException(String message) {
        super(message);
        this.idempotencyKey = null;
    }

    public IdempotencyException(String message, String idempotencyKey) {
        super(message);
        this.idempotencyKey = idempotencyKey;
    }

    public IdempotencyException(String message, Throwable cause) {
        super(message, cause);
        this.idempotencyKey = null;
    }

    public IdempotencyException(String message, String idempotencyKey, Throwable cause) {
        super(message, cause);
        this.idempotencyKey = idempotencyKey;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public boolean hasIdempotencyKey() {
        return idempotencyKey != null && !idempotencyKey.isBlank();
    }
}