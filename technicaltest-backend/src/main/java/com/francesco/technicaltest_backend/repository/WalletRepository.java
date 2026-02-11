package com.francesco.technicaltest_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francesco.technicaltest_backend.entity.WalletTransaction;

/**
 * Repository per l'entitá WalletTransaction.
 */
public interface WalletRepository extends JpaRepository<WalletTransaction, Long> {

    // Trova tutte le transazioni ordinate per data di creazione in ordine decrescente
    List<WalletTransaction> findAllByOrderByCreatedAtDesc();

    // Trova l'ultima transazione
    WalletTransaction findTopByOrderByCreatedAtDesc();
}
