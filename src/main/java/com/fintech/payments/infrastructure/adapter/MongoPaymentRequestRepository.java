package com.fintech.payments.infrastructure.adapter;

import com.fintech.payments.domain.model.IdempotencyKey;
import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.domain.model.PaymentStatus;
import com.fintech.payments.domain.port.PaymentRequestRepository;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.UUID;

/**
 * Adaptador para la persistencia de solicitudes de pago en MongoDB.
 * Implementa el puerto PaymentRequestRepository usando MongoDB con Panache
 * para operaciones reactivas.
 */
@ApplicationScoped
public class MongoPaymentRequestRepository implements PaymentRequestRepository, PanacheMongoRepositoryBase<PaymentRequest, ObjectId> {

    @Inject
    io.quarkus.mongodb.reactive.ReactiveMongoClient mongoClient;

    @Inject
    jakarta.inject.Provider<com.mongodb.reactivestreams.client.MongoDatabase> databaseProvider;

    @Override
    public Uni<PaymentRequest> save(PaymentRequest paymentRequest) {
        return Uni.createFrom().item(() -> {
            persist(paymentRequest);
            return paymentRequest;
        });
    }

    @Override
    public Uni<PaymentRequest> findById(UUID id) {
        return Uni.createFrom().completionStage(() -> 
            find("id", id).first().executeInsert()
                .thenCompose(result -> find("id", id).first())
        ).onItem().ifNull().continueWith(() -> null);
    }

    @Override
    public Uni<PaymentRequest> findByIdempotencyKey(IdempotencyKey idempotencyKey) {
        return Uni.createFrom().completionStage(() -> 
            find("idempotencyKey", idempotencyKey.value()).first()
        ).onItem().ifNull().continueWith(() -> null);
    }

    @Override
    public Uni<PaymentRequest> updateStatus(UUID id, PaymentStatus newStatus) {
        return findById(id).onItem().ifNotNull().transformToUni(existing -> {
            PaymentRequest updated = existing.withStatus(newStatus);
            return Uni.createFrom().item(() -> {
                update(updated);
                return updated;
            });
        });
    }

    /**
     * Busca solicitudes por status específico.
     * @param status el estado de las solicitudes a buscar
     * @return Uni con lista de solicitudes en el estado indicado
     */
    public Uni<java.util.List<PaymentRequest>> findByStatus(PaymentStatus status) {
        return Uni.createFrom().completionStage(() -> 
            find("status", status).list()
        );
    }

    /**
     * Cuenta el total de solicitudes en un estado dado.
     * @param status el estado a contar
     * @return Uni con el número de solicitudes
     */
    public Uni<Long> countByStatus(PaymentStatus status) {
        return Uni.createFrom().completionStage(() -> 
            count("status", status)
        );
    }

    /**
     * Elimina una solicitud por su ID.
     * @param id el identificador único de la solicitud
     * @return Uni que completa cuando la eliminación es exitosa
     */
    public Uni<Void> deleteById(UUID id) {
        return Uni.createFrom().completionStage(() -> 
            delete("id", id).executeInsert().thenApply(v -> (Void) null)
        );
    }

    /**
     * Actualiza los datos de riesgo de una solicitud.
     * @param id identificador de la solicitud
     * @param riskTransactionId ID de transacción del bureau
     * @return Uni con la solicitud actualizada
     */
    public Uni<PaymentRequest> updateRiskData(UUID id, String riskTransactionId) {
        return findById(id).onItem().ifNotNull().transformToUni(existing -> {
            PaymentRequest updated = existing.withRiskBureauTransactionId(riskTransactionId);
            return Uni.createFrom().item(() -> {
                update(updated);
                return updated;
            });
        });
    }

    /**
     * Actualiza el ID de transacción del core bancario.
     * @param id identificador de la solicitud
     * @param bankingTransactionId ID de transacción bancaria
     * @return Uni con la solicitud actualizada
     */
    public Uni<PaymentRequest> updateBankingData(UUID id, String bankingTransactionId) {
        return findById(id).onItem().ifNotNull().transformToUni(existing -> {
            PaymentRequest updated = existing.withCoreBankingTransactionId(bankingTransactionId);
            return Uni.createFrom().item(() -> {
                update(updated);
                return updated;
            });
        });
    }

    /**
     * Obtiene la base de datos configurada para este repositorio.
     * @return el nombre de la base de datos MongoDB
     */
    public String getDatabaseName() {
        return mongoClient.getDatabase("payment_requests").getName();
    }
}