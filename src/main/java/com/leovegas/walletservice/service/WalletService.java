package com.leovegas.walletservice.service;

import com.leovegas.walletservice.domain.entities.Wallet;
import com.leovegas.walletservice.domain.models.WalletFilter;
import org.springframework.validation.annotation.Validated;

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
}
