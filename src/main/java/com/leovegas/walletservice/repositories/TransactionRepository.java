package com.leovegas.walletservice.repositories;

import com.leovegas.walletservice.domain.entities.QTransaction;
import com.leovegas.walletservice.domain.entities.Transaction;
import com.leovegas.walletservice.domain.entities.Wallet;

import java.util.List;

/**
 * Repository for {@link Transaction}
 */
public interface TransactionRepository extends QueryDslRepository<Transaction, QTransaction, Long> {
    /**
     * Returns true if transaction with specified id already exists.
     *
     * @param transactionId transaction id
     * @return true/false
     */
    boolean existsByTransactionId(String transactionId);

    /**
     * Returns all transactions by wallet.
     *
     * @param wallet {@link Wallet}
     * @return list of {@link Transaction}
     */
    List<Transaction> findAllByWallet(Wallet wallet);
}
