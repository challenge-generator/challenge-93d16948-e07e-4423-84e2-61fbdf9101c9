package com.fintech.payments.domain.port;

import com.fintech.payments.domain.model.PaymentRequest;
import io.smallrye.mutiny.Uni;

/**
 * Puerto para interactuar con el buró de riesgos.
 * Define el contrato que la infraestructura debe implementar para consultar
 * el historial crediticio y evaluar el riesgo del solicitante.
 */
public interface RiskBureauService {

    /**
     * Consulta el buró de riesgos para validar la situación crediticia del originador.
     * @param paymentRequest la solicitud de pago a evaluar
     * @return Uni con la solicitud actualizada incluyendo el ID de transacción del bureau
     */
    Uni<PaymentRequest> checkRisk(PaymentRequest paymentRequest);

    /**
     * Verifica si el originador tiene restricciones o alertas activas.
     * @param originatorId identificador del originador de crédito
     * @return Uni con true si el originador está en lista de restricciones
     */
    Uni<Boolean> hasActiveRestrictions(String originatorId);
}