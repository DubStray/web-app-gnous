package com.francesco.technicaltest_backend.dtos;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * DTO per l'entitá WalletTransaction.
 */
@Data
public class WalletTransactionDTO {
    private Integer amount;
    private Integer balanceAfter;
    private String description;
    private Long taskId;
    private LocalDateTime timestamp;

}
