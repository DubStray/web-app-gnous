package com.francesco.technicaltest_backend.dtos;

import java.time.LocalDateTime;

import com.francesco.technicaltest_backend.entity.enums.AuditLogEventType;

import lombok.Data;

/**
 * DTO per il log di audit
 */
@Data
public class AuditLogDTO {
    private AuditLogEventType eventType;
    private String payload;
    private Long taskId;
    private Integer walletDelta;
    private LocalDateTime timestamp;
}
