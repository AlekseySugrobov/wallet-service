package com.leovegas.walletservice.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Describes information about wallet.
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "wallets")
public class Wallet extends BaseEntity {
    /**
     * User's identifier.
     */
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;
    /**
     * Balance.
     */
    @NotNull
    private BigDecimal balance = BigDecimal.ZERO;
}
