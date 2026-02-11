package com.francesco.technicaltest_backend.dtos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.francesco.technicaltest_backend.dtos.WalletTransactionDTO;
import com.francesco.technicaltest_backend.entity.WalletTransaction;

@Mapper(componentModel = "spring")
public interface WalletTransactionMapper {

    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "createdAt", target = "timestamp")
    WalletTransactionDTO toWalletTransactionDTO(WalletTransaction walletTransaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    WalletTransaction toWalletTransaction(WalletTransactionDTO walletTransactionDTO);

    List<WalletTransactionDTO> toWalletTransactionDTOList(List<WalletTransaction> walletTransactions);
}
