package com.fintech.payments.domain.port;


import com.fintech.payments.domain.model.PaymentStatus;
import com.fintech.payments.domain.model.IdempotencyKey;
import com.fintech.payments.domain.model.PaymentRequest;
import io.smallrye.mutiny.Uni;
import java.util.UUID;

/**
 * Puerto para persistencia de solicitudes de pago.
 * Define las operaciones necesarias para registrar y consultar solicitudes.
 */
public interface PaymentRequestRepository {

    /**
     * Guarda una solicitud de pago.
     *
     * @param paymentRequest Solicitud a guardar
     * @return Uni que emite la solicitud guardada
     */
    Uni<PaymentRequest> save(PaymentRequest paymentRequest);

    /**
     * Busca una solicitud de pago por su ID.
     *
     * @param id ID de la solicitud
     * @return Uni que emite la solicitud encontrada o null si no existe
     */
    Uni<PaymentRequest> findById(UUID id);

    /**
     * Busca una solicitud de pago por su clave de idempotencia.
     *
     * @param idempotencyKey Clave de idempotencia
     * @return Uni que emite la solicitud encontrada o null si no existe
     */
    Uni<PaymentRequest> findByIdempotencyKey(IdempotencyKey idempotencyKey);

    /**
     * Actualiza el estado de una solicitud de pago.
     *
     * @param id ID de la solicitud
     * @param newStatus Nuevo estado
     * @return Uni que emite la solicitud actualizada
     */
    Uni<PaymentRequest> updateStatus(UUID id, PaymentStatus newStatus);
}