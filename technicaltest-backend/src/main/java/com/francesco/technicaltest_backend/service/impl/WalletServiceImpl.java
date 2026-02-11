package com.francesco.technicaltest_backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.francesco.technicaltest_backend.dtos.WalletTransactionDTO;
import com.francesco.technicaltest_backend.dtos.mapper.WalletTransactionMapper;
import com.francesco.technicaltest_backend.entity.Task;
import com.francesco.technicaltest_backend.entity.WalletTransaction;
import com.francesco.technicaltest_backend.entity.enums.AuditLogEventType;
import com.francesco.technicaltest_backend.exception.wallet.WalletExceptions.InsufficientCreditsException;
import com.francesco.technicaltest_backend.repository.WalletRepository;
import com.francesco.technicaltest_backend.service.AuditLogService;
import com.francesco.technicaltest_backend.service.WalletService;

import jakarta.transaction.Transactional;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionMapper mapper;
    private final AuditLogService auditLogService;

    // Costanti per le operazioni sul wallet
    private final Integer TASK_COST = 1;
    private final Integer TASK_COMPLETION_REWARD = 2;
    private final Integer TASK_DELETION_REFUND = 1;

    // Costante per il saldo iniziale
    private final Integer INITIAL_BALANCE = 100;

    public WalletServiceImpl(WalletRepository walletRepository, WalletTransactionMapper mapper, AuditLogService auditLogService) {
        this.walletRepository = walletRepository;
        this.mapper = mapper;
        this.auditLogService = auditLogService;
    }
    
    @Override
    @Transactional
    public void initWallet() {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setAmount(INITIAL_BALANCE);
        transaction.setBalanceAfter(INITIAL_BALANCE);
        transaction.setDescription("Initial Balance");

        walletRepository.save(transaction);
    }

    @Override
    public Integer getCurrentBalance() {
        // Recupero l'ultima transazione
        WalletTransaction latestTransaction = walletRepository.findTopByOrderByCreatedAtDesc();
        
        if (latestTransaction == null) {
            initWallet();
            // semplicemente riprova a recuperare l'ultima transazione dopo l'inizializzazione del wallet
            latestTransaction = walletRepository.findTopByOrderByCreatedAtDesc();
        }

        return latestTransaction.getBalanceAfter();
    }

    // Metodo per scalare il wallet
    // @Transactional per effetuare rollback sul DB in caso di errore
    @Override
    @Transactional
    public void debit(Integer amount, Task task, String description) {
        Integer currentBalance = getCurrentBalance();

        // check per vedere se ci sono abbastanza crediti
        if (currentBalance < amount) {
            throw new InsufficientCreditsException();
        }

        Integer newBalance = currentBalance - amount;

        WalletTransaction transaction = new WalletTransaction();
        transaction.setAmount(-amount); // negativo per indicare una spesa
        transaction.setBalanceAfter(newBalance);
        transaction.setDescription(description);
        transaction.setTask(task);

        walletRepository.save(transaction);

        this.auditLogService.logEvent(
            AuditLogEventType.WALLET_DEBIT, 
            description, 
            task, 
            newBalance);;
    }

    // Metodo per incrementare il wallet
    // @Transactional per effetuare rollback sul DB in caso di errore
    @Override
    @Transactional
    public void credit(Integer amount, Task task, String description) {
        Integer currentBalance = getCurrentBalance();

        Integer newBalance = currentBalance + amount;

        WalletTransaction transaction = new WalletTransaction();
        transaction.setAmount(amount);
        transaction.setBalanceAfter(newBalance);
        transaction.setDescription(description);
        transaction.setTask(task);

        walletRepository.save(transaction);

        this.auditLogService.logEvent(
            AuditLogEventType.WALLET_CREDIT, 
            description, 
            task, 
            newBalance);
    }

    @Override
    public List<WalletTransactionDTO> getAllHistory() {
        List<WalletTransaction> transactions = this.walletRepository.findAllByOrderByCreatedAtDesc();

        return this.mapper.toWalletTransactionDTOList(transactions);
    }

    @Override
    public Integer getTaskCost() {
        return TASK_COST;
    }

    @Override
    public Integer getTaskCompletionReward() {
        return TASK_COMPLETION_REWARD;
    }

    @Override
    public Integer getTaskDeletionRefund() {
        return TASK_DELETION_REFUND;
    }

}
