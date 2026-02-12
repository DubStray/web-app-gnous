package com.francesco.technicaltest_backend.service;

import java.util.List;

import com.francesco.technicaltest_backend.entity.Task;

import com.francesco.technicaltest_backend.dtos.WalletTransactionDTO;

/**
 * Interfaccia Service per l'entitá WalletTransaction.
 */
public interface WalletService {

    Integer getCurrentBalance();

    // Metodo per scalare il wallet
    void debit(Integer amount, Task task, String description);

    // Metodo per incrementare il wallet
    void credit(Integer amount, Task task, String description);

    // Metodo per inizializzare il wallet
    void initWallet();

    List<WalletTransactionDTO> getAllHistory();

    Integer getTaskCost();

    Integer getTaskCompletionReward();

    Integer getTaskDeletionRefund();

    // Metodo per sgangiare le transazioni da una task (per il delete)
    void detachFromTask(Task task);
}
