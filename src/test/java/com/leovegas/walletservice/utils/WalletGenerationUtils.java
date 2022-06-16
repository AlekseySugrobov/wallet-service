package com.leovegas.walletservice.utils;

import com.leovegas.walletservice.domain.entities.Wallet;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;

public class WalletGenerationUtils {


    public static Wallet generateWallet() {
        Wallet wallet = new Wallet();
        wallet.setUserId(RandomUtils.nextLong());
        wallet.setBalance(BigDecimal.valueOf(RandomUtils.nextDouble(0.0, 10_000_000.0)));
        return wallet;
    }

    private WalletGenerationUtils() {

    }
}
