package com.fintech.payments.infrastructure.exception;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.util.Map;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Inject
    public GlobalExceptionHandler() {
    }

    @Override
    public Response toResponse(Exception exception) {
        LOG.error("Unhandled exception in payment processing", exception);

        if (exception instanceof IdempotencyException idempotencyException) {
            return handleIdempotencyException(idempotencyException);
        }

        if (exception instanceof RiskBureauTimeoutException riskTimeoutException) {
            return handleRiskBureauTimeoutException(riskTimeoutException);
        }

        if (exception instanceof CoreBankingException coreBankingException) {
            return handleCoreBankingException(coreBankingException);
        }

        return handleGenericException(exception);
    }

    private Response handleIdempotencyException(IdempotencyException ex) {
        LOG.warn("Idempotency violation: " + ex.getMessage());
        return Response.status(Response.Status.CONFLICT)
                .entity(Map.of(
                        "error", "CONFLICT",
                        "message", ex.getMessage(),
                        "existingRequestId", ex.getExistingRequestId() != null ? ex.getExistingRequestId().toString() : null
                ))
                .build();
    }

    private Response handleRiskBureauTimeoutException(RiskBureauTimeoutException ex) {
        LOG.error("Risk bureau timeout for transaction: " + ex.getTransactionId(), ex);
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(Map.of(
                        "error", "RISK_BUREAU_TIMEOUT",
                        "message", ex.getMessage(),
                        "transactionId", ex.getTransactionId() != null ? ex.getTransactionId() : "unknown",
                        "retryable", true
                ))
                .build();
    }

    private Response handleCoreBankingException(CoreBankingException ex) {
        LOG.error("Core banking error for transaction: " + ex.getTransactionId(), ex);
        int statusCode = ex.getHttpStatusCode();
        if (statusCode < 400 || statusCode >= 600) {
            statusCode = 500;
        }

        return Response.status(statusCode)
                .entity(Map.of(
                        "error", "CORE_BANKING_ERROR",
                        "message", ex.getMessage(),
                        "transactionId", ex.getTransactionId() != null ? ex.getTransactionId() : "unknown",
                        "retryable", ex.isRetryable()
                ))
                .build();
    }

    private Response handleGenericException(Exception ex) {
        LOG.error("Unexpected error in payment processing", ex);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of(
                        "error", "INTERNAL_ERROR",
                        "message", "An unexpected error occurred while processing the payment"
                ))
                .build();
    }
}