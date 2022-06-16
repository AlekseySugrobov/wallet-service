package com.leovegas.walletservice.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Describes filter for searching players.
 */
@Data
@NoArgsConstructor
public class PlayerFilter {
    /**
     * First name must contain.
     */
    private String firstName;
    /**
     * Last name must contain.
     */
    private String lastName;
    /**
     * Start balance.
     */
    private BigDecimal fromBalance;
    /**
     * End balance.
     */
    private BigDecimal toBalance;
}
