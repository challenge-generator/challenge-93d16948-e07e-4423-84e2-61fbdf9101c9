package com.fintech.payments.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa una solicitud de pago con garantía de idempotencia.
 * Contiene la información necesaria para procesar un pago y su estado actual.
 */
public record PaymentRequest(
    UUID id,
    IdempotencyKey idempotencyKey,
    String accountId,
    String recipientAccount,
    BigDecimal amount,
    String currency,
    PaymentStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    int retryCount,
    String riskBureauTransactionId,
    String coreBankingTransactionId) {

    /**
     * Constructor que inicializa una solicitud de pago con los valores requeridos.
     *
     * @param idempotencyKey Clave de idempotencia para garantizar que la solicitud no se procese duplicada
     * @param accountId ID de la cuenta origen
     * @param recipientAccount ID de la cuenta destino
     * @param amount Monto a transferir
     * @param currency Moneda de la transferencia
     */
    public PaymentRequest {
        Objects.requireNonNull(idempotencyKey, "La clave de idempotencia no puede ser nula");
        Objects.requireNonNull(accountId, "El ID de cuenta origen no puede ser nulo");
        Objects.requireNonNull(recipientAccount, "El ID de cuenta destino no puede ser nulo");
        Objects.requireNonNull(amount, "El monto no puede ser nulo");
        Objects.requireNonNull(currency, "La moneda no puede ser nula");
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        
        if (currency.length() != 3) {
            throw new IllegalArgumentException("La moneda debe ser un código de 3 letras");
        }
    }

    /**
     * Crea una nueva solicitud de pago con estado PENDING.
     *
     * @param idempotencyKey Clave de idempotencia
     * @param accountId ID de la cuenta origen
     * @param recipientAccount ID de la cuenta destino
     * @param amount Monto a transferir
     * @param currency Moneda de la transferencia
     * @return Nueva instancia de PaymentRequest
     */
    public static PaymentRequest createNew(
            IdempotencyKey idempotencyKey,
            String accountId,
            String recipientAccount,
            BigDecimal amount,
            String currency) {
        return new PaymentRequest(
            UUID.randomUUID(),
            idempotencyKey,
            accountId,
            recipientAccount,
            amount,
            currency,
            PaymentStatus.PENDING,
            LocalDateTime.now(),
            LocalDateTime.now(),
            0,
            null,
            null
        );
    }

    /**
     * Actualiza el estado de la solicitud de pago.
     *
     * @param newStatus Nuevo estado
     * @return Nueva instancia con el estado actualizado
     */
    public PaymentRequest withStatus(PaymentStatus newStatus) {
        return new PaymentRequest(
            this.id,
            this.idempotencyKey,
            this.accountId,
            this.recipientAccount,
            this.amount,
            this.currency,
            newStatus,
            this.createdAt,
            LocalDateTime.now(),
            this.retryCount,
            this.riskBureauTransactionId,
            this.coreBankingTransactionId
        );
    }

    /**
     * Incrementa el contador de reintentos.
     *
     * @return Nueva instancia con el contador incrementado
     */
    public PaymentRequest incrementRetryCount() {
        return new PaymentRequest(
            this.id,
            this.idempotencyKey,
            this.accountId,
            this.recipientAccount,
            this.amount,
            this.currency,
            this.status,
            this.createdAt,
            LocalDateTime.now(),
            this.retryCount + 1,
            this.riskBureauTransactionId,
            this.coreBankingTransactionId
        );
    }

    /**
     * Actualiza el ID de transacción del buró de riesgos.
     *
     * @param transactionId ID de transacción
     * @return Nueva instancia con el ID actualizado
     */
    public PaymentRequest withRiskBureauTransactionId(String transactionId) {
        return new PaymentRequest(
            this.id,
            this.idempotencyKey,
            this.accountId,
            this.recipientAccount,
            this.amount,
            this.currency,
            this.status,
            this.createdAt,
            LocalDateTime.now(),
            this.retryCount,
            transactionId,
            this.coreBankingTransactionId
        );
    }

    /**
     * Actualiza el ID de transacción del core bancario.
     *
     * @param transactionId ID de transacción
     * @return Nueva instancia con el ID actualizado
     */
    public PaymentRequest withCoreBankingTransactionId(String transactionId) {
        return new PaymentRequest(
            this.id,
            this.idempotencyKey,
            this.accountId,
            this.recipientAccount,
            this.amount,
            this.currency,
            this.status,
            this.createdAt,
            LocalDateTime.now(),
            this.retryCount,
            this.riskBureauTransactionId,
            transactionId
        );
    }
}