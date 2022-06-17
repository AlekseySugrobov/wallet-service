package com.leovegas.walletservice.service;

import com.leovegas.model.RegisterTransactionRequest;
import com.leovegas.walletservice.domain.entities.Transaction;

import java.util.List;

/**
 * Describes contract for transactions business logic.
 */
public interface TransactionService {
    /**
     * Handling transaction request.
     *
     * @param registerTransactionRequest request for registering transaction {@link RegisterTransactionRequest}
     * @return created transaction {@link Transaction}
     */
    Transaction handleTransaction(RegisterTransactionRequest registerTransactionRequest);

    /**
     * Returns transactions by user id.
     *
     * @param userId user id
     * @return list of {@link Transaction}
     */
    List<Transaction> getTransactionsByUserId(Long userId);
}
