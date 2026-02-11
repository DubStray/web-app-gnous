package com.francesco.technicaltest_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.francesco.technicaltest_backend.dtos.AuditLogDTO;
import com.francesco.technicaltest_backend.service.AuditLogService;
/**
 * Controller per la gestione dei log di audit.
 */
@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditLogService auditLogService;

    public AuditController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping("/logs")
    public ResponseEntity<List<AuditLogDTO>> getLogs() {
        return ResponseEntity.ok(this.auditLogService.getAllLogs());
    }
}
