package com.francesco.technicaltest_backend.service;

import java.util.List;

import com.francesco.technicaltest_backend.dtos.AuditLogDTO;
import com.francesco.technicaltest_backend.entity.Task;
import com.francesco.technicaltest_backend.entity.enums.AuditLogEventType;

/**
 * Interfaccia per il servizio di audit log.
 * Gestisce la registrazione e il recupero dei log di audit.
 */
public interface AuditLogService {

    void logEvent(AuditLogEventType eventType, String payload, Task task, Integer walletDelta);

    List<AuditLogDTO> getAllLogs();

    List<AuditLogDTO> getLogsByType(AuditLogEventType eventType);

    void detachFromTask(Task task);
}
