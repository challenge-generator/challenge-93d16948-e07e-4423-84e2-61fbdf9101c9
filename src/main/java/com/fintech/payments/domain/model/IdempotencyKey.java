package com.fintech.payments.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Clase de valor que representa una clave de idempotencia.
 * Garantiza que las solicitudes de pago no se procesen duplicadas.
 */
public record IdempotencyKey(String value) {

    /**
     * Constructor que valida el formato de la clave de idempotencia.
     *
     * @param value Valor de la clave
     */
    public IdempotencyKey {
        Objects.requireNonNull(value, "La clave de idempotencia no puede ser nula");
        
        if (value.isBlank()) {
            throw new IllegalArgumentException("La clave de idempotencia no puede estar vacía");
        }
        
        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La clave de idempotencia debe ser un UUID válido", e);
        }
    }

    /**
     * Genera una nueva clave de idempotencia usando UUID v7.
     *
     * @return Nueva instancia de IdempotencyKey
     */
    public static IdempotencyKey generate() {
        return new IdempotencyKey(UUID.randomUUID().toString());
    }
}