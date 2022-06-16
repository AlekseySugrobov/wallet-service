package com.leovegas.walletservice.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Describes filter for searching wallets.
 */
@Data
@NoArgsConstructor
public class WalletFilter {
    /**
     * User's identifier
     */
    private Long userId;
    /**
     * Start balance.
     */
    private BigDecimal fromBalance;
    /**
     * End balance.
     */
    private BigDecimal toBalance;
}
