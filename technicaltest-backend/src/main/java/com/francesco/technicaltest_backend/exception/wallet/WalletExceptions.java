package com.francesco.technicaltest_backend.exception.wallet;

import org.springframework.http.HttpStatus;

import com.francesco.technicaltest_backend.exception.BusinessException;

/**
 * Classe che contiene le eccezioni relative all'entità Wallet
 */
public class WalletExceptions extends RuntimeException {

    public static class InsufficientCreditsException extends BusinessException {
        public InsufficientCreditsException() {
            super("Insufficient credits", 
                HttpStatus.BAD_REQUEST, 
                "Insufficient credits to perform the operation");
        }
    }

    public static class NoTransactionFoundException extends BusinessException {
        public NoTransactionFoundException() {
            super("No transaction found", 
                HttpStatus.NOT_FOUND, 
                "No transaction found");
        }
    }
}
