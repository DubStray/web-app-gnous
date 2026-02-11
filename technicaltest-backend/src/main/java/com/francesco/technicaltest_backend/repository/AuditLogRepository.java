package com.francesco.technicaltest_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francesco.technicaltest_backend.entity.AuditLog;
import com.francesco.technicaltest_backend.entity.enums.AuditLogEventType;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    // Trova tutti i log di audit ordinati per timestamp decrescente
    List<AuditLog> findAllByOrderByTimestampDesc();

    // Trova tutti i log di audit di un certo tipo
    List<AuditLog> findByEventType(AuditLogEventType eventType);
}
