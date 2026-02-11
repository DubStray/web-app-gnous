package com.francesco.technicaltest_backend.dtos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.francesco.technicaltest_backend.dtos.AuditLogDTO;
import com.francesco.technicaltest_backend.entity.AuditLog;

/**
 * Mapper per la conversione tra AuditLog e AuditLogDTO
 */
@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    @Mapping(source = "task.id", target = "taskId")
    AuditLogDTO toDTO(AuditLog auditLog);

    List<AuditLogDTO> toDTOs(List<AuditLog> auditLogs);
}
