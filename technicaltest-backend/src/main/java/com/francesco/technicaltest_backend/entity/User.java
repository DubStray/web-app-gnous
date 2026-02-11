package com.francesco.technicaltest_backend.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Table;

/**
 * Entitá User che rappresenta un utente del sistema, con questi attributi:
 * - id: identificatore univoco dell'utente
 * - username: nome dell'utente
 * - wallet: saldo dell'utente
 * - createdAt: data di creazione dell'utente
 * - tasks: lista di task associati all'utente
 * 
 * @author Francesco
 */
@Entity
@Table(name = "users")
@Data
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "wallet")
    private int wallet;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
