package com.leovegas.walletservice.repositories;

import com.leovegas.walletservice.domain.entities.QWallet;
import com.leovegas.walletservice.domain.entities.Wallet;

import java.util.Optional;

/**
 * Repository for {@link Wallet}.
 */
public interface WalletRepository extends QueryDslRepository<Wallet, QWallet, Long> {
    /**
     * Returns wallet by user id.
     *
     * @param userId user id
     * @return Optional of {@link Wallet}
     */
    Optional<Wallet> findByUserId(long userId);

    /**
     * Returns true if wallet with specified user id exists.
     *
     * @param userId user id
     * @return true/false
     */
    boolean existsByUserId(long userId);

    /**
     * Deletes wallet by user id.
     *
     * @param userId user id
     */
    void deleteByUserId(long userId);
}
