package com.francesco.technicaltest_backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.francesco.technicaltest_backend.dtos.WalletTransactionDTO;
import com.francesco.technicaltest_backend.service.WalletService;

/**
 * Controller per la gestione del wallet
 */
@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // API per ottenere il saldo del wallet
    @GetMapping("/balance")
    public ResponseEntity<Map<String, Integer>> getBalance() {
        Map<String, Integer> response = new HashMap<>();
        response.put("balance", walletService.getCurrentBalance());

        return ResponseEntity.ok(response);
    }

    // API per ottenere la history delle transazioni
    @GetMapping("/history")
    public ResponseEntity<List<WalletTransactionDTO>> getHistory() {
        return ResponseEntity.ok(walletService.getAllHistory());
    }
}
