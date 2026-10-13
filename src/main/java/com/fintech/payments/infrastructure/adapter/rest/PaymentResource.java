package com.fintech.payments.infrastructure.adapter.rest;

import com.fintech.payments.application.service.PaymentService;
import com.fintech.payments.domain.model.IdempotencyKey;
import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.infrastructure.exception.IdempotencyException;
import com.fintech.payments.infrastructure.exception.RiskBureauTimeoutException;
import com.fintech.payments.infrastructure.exception.CoreBankingException;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.Map;

@Path("/api/v1/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    private static final Logger LOG = Logger.getLogger(PaymentResource.class);
    private static final String IDEMPOTENCY_KEY_HEADER = "X-Idempotency-Key";

    @Inject
    PaymentService paymentService;

    @POST
    @Path("/submit")
    public Uni<Response> submitPaymentRequest(
            @HeaderParam(IDEMPOTENCY_KEY_HEADER) String idempotencyKeyHeader,
            PaymentRequest paymentRequest) {

        LOG.infof("Received payment request submission, idempotencyKey: %s", idempotencyKeyHeader);

        if (idempotencyKeyHeader == null || idempotencyKeyHeader.isBlank()) {
            return Uni.createFrom().item(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(Map.of(
                                    "error", "MISSING_IDEMPOTENCY_KEY",
                                    "message", "X-Idempotency-Key header is required"
                            ))
                            .build()
            );
        }

        IdempotencyKey idempotencyKey;
        try {
            idempotencyKey = new IdempotencyKey(idempotencyKeyHeader);
        } catch (IllegalArgumentException e) {
            return Uni.createFrom().item(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(Map.of(
                                    "error", "INVALID_IDEMPOTENCY_KEY",
                                    "message", "Idempotency key must be a valid UUID"
                            ))
                            .build()
            );
        }

        return paymentService.submitPaymentRequest(paymentRequest, idempotencyKey)
                .onItem().transform(paymentReq -> {
                    LOG.infof("Payment request processed successfully, id: %s, status: %s",
                            paymentReq.id(), paymentReq.status());
                    return Response.status(Response.Status.CREATED)
                            .entity(Map.of(
                                    "id", paymentReq.id().toString(),
                                    "status", paymentReq.status().name(),
                                    "idempotencyKey", idempotencyKey.value(),
                                    "createdAt", paymentReq.createdAt().toString()
                            ))
                            .build();
                })
                .onFailure(IdempotencyException.class).recoverWithItem(ex -> {
                    LOG.warn("Idempotency conflict: " + ex.getMessage());
                    IdempotencyException ide = (IdempotencyException) ex;
                    return Response.status(Response.Status.CONFLICT)
                            .entity(Map.of(
                                    "error", "CONFLICT",
                                    "message", ex.getMessage(),
                                    "existingRequestId", ide.getExistingRequestId() != null ?
                                            ide.getExistingRequestId().toString() : "unknown"
                            ))
                            .build();
                })
                .onFailure(RiskBureauTimeoutException.class).recoverWithItem(ex -> {
                    LOG.error("Risk bureau timeout: " + ex.getMessage());
                    return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                            .entity(Map.of(
                                    "error", "RISK_BUREAU_UNAVAILABLE",
                                    "message", "Risk bureau service is temporarily unavailable",
                                    "retryable", true
                            ))
                            .build();
                })
                .onFailure(CoreBankingException.class).recoverWithItem(ex -> {
                    LOG.error("Core banking error: " + ex.getMessage());
                    CoreBankingException cbe = (CoreBankingException) ex;
                    int statusCode = cbe.getHttpStatusCode();
                    if (statusCode < 400 || statusCode >= 600) {
                        statusCode = cbe.isRetryable() ? 503 : 500;
                    }
                    return Response.status(statusCode)
                            .entity(Map.of(
                                    "error", "CORE_BANKING_ERROR",
                                    "message", ex.getMessage(),
                                    "retryable", cbe.isRetryable()
                            ))
                            .build();
                })
                .onFailure().recoverWithItem(ex -> {
                    LOG.error("Unexpected error processing payment request", ex);
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(Map.of(
                                    "error", "INTERNAL_ERROR",
                                    "message", "An unexpected error occurred"
                            ))
                            .build();
                });
    }

    @GET
    @Path("/health")
    public Uni<Response> health() {
        return Uni.createFrom().item(
                Response.ok(Map.of(
                        "status", "UP",
                        "service", "payment-processing"
                )).build()
        );
    }
}