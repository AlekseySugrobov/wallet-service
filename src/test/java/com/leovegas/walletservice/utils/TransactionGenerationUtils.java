package com.leovegas.walletservice.utils;

import com.leovegas.model.RegisterTransactionRequest;
import com.leovegas.walletservice.domain.entities.TransactionType;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;

public class TransactionGenerationUtils {

    public static RegisterTransactionRequest generateRegisterTransactionRequest(BigDecimal amount,
                                                                                TransactionType type,
                                                                                long userId,
                                                                                String transactionId) {
        RegisterTransactionRequest registerTransactionRequest = new RegisterTransactionRequest();
        registerTransactionRequest.setAmount(amount);
        registerTransactionRequest.setType(type);
        registerTransactionRequest.setUserId(userId);
        registerTransactionRequest.setTransactionId(transactionId);
        return registerTransactionRequest;
    }

    public static RegisterTransactionRequest generateRegisterTransactionRequest() {
        RegisterTransactionRequest registerTransactionRequest = new RegisterTransactionRequest();
        registerTransactionRequest.setAmount(BigDecimal.TEN);
        registerTransactionRequest.setType(TransactionType.CREDIT);
        registerTransactionRequest.setTransactionId(RandomStringUtils.randomAlphabetic(10));
        registerTransactionRequest.setUserId(RandomUtils.nextLong());
        return registerTransactionRequest;
    }

    private TransactionGenerationUtils() {

    }
}
