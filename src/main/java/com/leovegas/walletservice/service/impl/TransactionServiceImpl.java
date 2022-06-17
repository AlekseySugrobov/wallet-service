package com.leovegas.walletservice.service.impl;

import com.leovegas.model.RegisterTransactionRequest;
import com.leovegas.walletservice.domain.entities.Transaction;
import com.leovegas.walletservice.domain.entities.TransactionStatus;
import com.leovegas.walletservice.domain.entities.Wallet;
import com.leovegas.walletservice.exceptions.TransactionIdAlreadyExists;
import com.leovegas.walletservice.repositories.TransactionRepository;
import com.leovegas.walletservice.service.TransactionService;
import com.leovegas.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TransactionServiceImpl implements TransactionService {
    private final WalletService walletService;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Transaction handleTransaction(RegisterTransactionRequest registerTransactionRequest) {
        if (this.transactionRepository.existsByTransactionId(registerTransactionRequest.getTransactionId())) {
            throw new TransactionIdAlreadyExists(registerTransactionRequest.getTransactionId());
        }
        Wallet wallet = this.walletService.get(registerTransactionRequest.getUserId());
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setTransactionId(registerTransactionRequest.getTransactionId());
        transaction.setType(registerTransactionRequest.getType());
        transaction.setAmount(registerTransactionRequest.getAmount());
        try {
            this.walletService.proceedTransaction(wallet, registerTransactionRequest.getAmount(), registerTransactionRequest.getType());
            transaction.setStatus(TransactionStatus.SUCCESS);
        } catch (Exception exception) {
            transaction.setStatus(TransactionStatus.FAILED);
            transaction.setComment(exception.getMessage());
        }
        return this.transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByUserId(Long userId) {
        Wallet wallet = this.walletService.get(userId);
        return this.transactionRepository.findAllByWallet(wallet);
    }
}
