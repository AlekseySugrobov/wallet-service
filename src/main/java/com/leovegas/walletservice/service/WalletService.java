package com.leovegas.walletservice.service;

import com.leovegas.model.WalletFilter;
import com.leovegas.walletservice.domain.entities.TransactionType;
import com.leovegas.walletservice.domain.entities.Wallet;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Describes contract for interaction with wallet entity.
 */
@Validated
public interface WalletService {
    /**
     * Creates new wallet for user.
     *
     * @param userId user's identifer
     * @return created wallet {@link Wallet}
     */
    Wallet create(long userId);

    /**
     * Returns wallet by user id.
     *
     * @param userId user's identifier
     * @return {@link Wallet}
     */
    Wallet get(long userId);

    /**
     * Deletes wallet by user id.
     *
     * @param userId user's identifier
     */
    void delete(long userId);

    /**
     * Returns wallets by filter.
     *
     * @param walletFilter {@link WalletFilter}
     * @return collection of {@link Wallet}
     */
    List<Wallet> search(WalletFilter walletFilter);

    /**
     * Returns balance for user id.
     *
     * @param userId user's identifier
     * @return balance
     */
    BigDecimal getBalanceByUserId(long userId);

    /**
     * Updates wallet balance.
     *
     * @param wallet {@link Wallet}
     * @param amount transaction's amount
     * @param type   transaction type {@link TransactionType}
     */
    void proceedTransaction(@NotNull @Valid Wallet wallet, @NotNull BigDecimal amount, @NotNull TransactionType type);
}
