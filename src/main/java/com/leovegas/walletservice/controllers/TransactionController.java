package com.leovegas.walletservice.controllers;

import com.leovegas.api.TransactionApi;
import com.leovegas.model.RegisterTransactionRequest;
import com.leovegas.model.TransactionDto;
import com.leovegas.walletservice.domain.entities.Transaction;
import com.leovegas.walletservice.mappers.TransactionMapper;
import com.leovegas.walletservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TransactionController implements TransactionApi {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Override
    public ResponseEntity<TransactionDto> apiV1TransactionPost(RegisterTransactionRequest registerTransactionRequest) {
        Transaction transaction = this.transactionService.handleTransaction(registerTransactionRequest);
        TransactionDto transactionDto = this.transactionMapper.transactionToTransactionDto(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionDto);
    }

    @Override
    public ResponseEntity<List<TransactionDto>> apiV1TransactionUserIdGet(Long userId) {
        List<Transaction> transactions = this.transactionService.getTransactionsByUserId(userId);
        List<TransactionDto> transactionDtoList = this.transactionMapper.transactionListToTransactionDtoList(transactions);
        return ResponseEntity.status(transactions.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(transactionDtoList);
    }
}
