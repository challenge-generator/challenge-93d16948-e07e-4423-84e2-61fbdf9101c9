package com.fintech.payments.domain.model;

/**
 * Estados posibles de una solicitud de pago.
 */
public enum PaymentStatus {
    PENDING,
    RISK_CHECK_IN_PROGRESS,
    RISK_CHECK_COMPLETED,
    CORE_BANKING_IN_PROGRESS,
    COMPLETED,
    FAILED,
    REJECTED_BY_RISK_BUREAU
}