package com.leovegas.walletservice.service;

import com.leovegas.model.RegisterTransactionRequest;
import com.leovegas.walletservice.domain.entities.Transaction;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Describes contract for transactions business logic.
 */
@Validated
public interface TransactionService {
    /**
     * Handling transaction request.
     *
     * @param registerTransactionRequest request for registering transaction {@link RegisterTransactionRequest}
     * @return created transaction {@link Transaction}
     */
    Transaction handleTransaction(@Valid RegisterTransactionRequest registerTransactionRequest);

    /**
     * Returns transactions by user id.
     *
     * @param userId user id
     * @return list of {@link Transaction}
     */
    List<Transaction> getTransactionsByUserId(@NotNull Long userId);
}
