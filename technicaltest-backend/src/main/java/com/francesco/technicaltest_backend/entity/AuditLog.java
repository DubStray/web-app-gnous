package com.francesco.technicaltest_backend.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.francesco.technicaltest_backend.entity.enums.AuditLogEventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entitá AuditLog che rappresenta un log di audit del sistema, con questi attributi:
 * - id: id del log
 * - eventType: tipo di evento del log gestito dall'enum AuditLogEventType
 * - timestamp: data e ora del log
 * - payload: payload JSON del log
 * - taskId: id del task a cui appartiene il log
 * - walletDelta: delta wallet del log
 */
@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditLogEventType eventType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    private String payload;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private Integer walletDelta;

}
