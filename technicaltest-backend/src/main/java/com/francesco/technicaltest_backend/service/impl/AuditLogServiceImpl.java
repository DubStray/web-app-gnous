package com.francesco.technicaltest_backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.francesco.technicaltest_backend.dtos.AuditLogDTO;
import com.francesco.technicaltest_backend.dtos.mapper.AuditLogMapper;
import com.francesco.technicaltest_backend.entity.AuditLog;
import com.francesco.technicaltest_backend.entity.Task;
import com.francesco.technicaltest_backend.entity.enums.AuditLogEventType;
import com.francesco.technicaltest_backend.repository.AuditLogRepository;
import com.francesco.technicaltest_backend.service.AuditLogService;

import jakarta.transaction.Transactional;

/**
 * Implementazione del servizio di audit log.
 * Gestisce la registrazione e il recupero dei log di audit.
 */
@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditRepository;
    private final AuditLogMapper mapper;

    public AuditLogServiceImpl(AuditLogRepository auditRepository, AuditLogMapper mapper) {
        this.auditRepository = auditRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void logEvent(AuditLogEventType eventType, String payload, Task task, Integer walletDelta) {
        AuditLog log = new AuditLog();
        log.setEventType(eventType);
        log.setPayload(payload);
        log.setTask(task);
        log.setWalletDelta(walletDelta);

        auditRepository.save(log);
    }


    // Potrebbe ritornare una lista vuota se non ci sono log
    @Override
    public List<AuditLogDTO> getAllLogs() {
        return this.mapper.toDTOs(this.auditRepository.findAllByOrderByTimestampDesc());
    }

    @Override
    public List<AuditLogDTO> getLogsByType(AuditLogEventType eventType) {
        return this.mapper.toDTOs(this.auditRepository.findByEventType(eventType));
    }

}
