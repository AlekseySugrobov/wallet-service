package com.leovegas.walletservice.repositories;

import com.leovegas.walletservice.domain.entities.QWallet;
import com.leovegas.walletservice.domain.entities.Wallet;

import java.util.Optional;

public interface WalletRepository extends QueryDslRepository<Wallet, QWallet, Long> {
    Optional<Wallet> findByUserId(long userId);

    boolean existsByUserId(long userId);

    void deleteByUserId(long userId);
}
