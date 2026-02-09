package com.francesco.technicaltest_backend.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    // Inizializzato di default a 100
    @Column(name = "wallet")
    private int wallet = 100;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;
}
