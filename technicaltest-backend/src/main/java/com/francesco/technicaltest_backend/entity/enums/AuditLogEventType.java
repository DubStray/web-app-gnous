package com.francesco.technicaltest_backend.entity.enums;

/**
 * Enum che rappresenta il tipo di evento di un audit log
 */
public enum AuditLogEventType {
    TASK_CREATED,
    TASK_UPDATED,
    TASK_STATUS_CHANGED,
    TASK_DELETED,
    WALLET_DEBIT,
    WALLET_CREDIT
}
