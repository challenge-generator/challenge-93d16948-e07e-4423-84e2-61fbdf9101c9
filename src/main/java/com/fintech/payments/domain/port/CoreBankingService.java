package com.fintech.payments.domain.port;

import com.fintech.payments.domain.model.PaymentRequest;
import io.smallrye.mutiny.Uni;

/**
 * Puerto para interactuar con el core bancario.
 * Define el contrato que la infraestructura debe implementar para procesar
 * transacciones financieras a través del sistema bancario central.
 */
public interface CoreBankingService {

    /**
     * Procesa un pago a través del core bancario.
     * @param paymentRequest la solicitud de pago a procesar
     * @return Uni con la solicitud actualizada incluyendo el ID de transacción bancaria
     */
    Uni<PaymentRequest> processPayment(PaymentRequest paymentRequest);

    /**
     * Consulta el saldo disponible del originador.
     * @param originatorId identificador del originador
     * @param amount monto a verificar
     * @return Uni con true si hay saldo suficiente
     */
    Uni<Boolean> verifyAvailableFunds(String originatorId, java.math.BigDecimal amount);

    /**
     * Revierte una transacción previamente procesada.
     * @param transactionId ID de la transacción a revertir
     * @return Uni void que completa cuando la reversión es exitosa
     */
    Uni<Void> reverseTransaction(String transactionId);
}