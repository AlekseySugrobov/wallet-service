package com.leovegas.walletservice.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Describes information about player.
 */
@Entity
@Getter
@Setter
@Table(name = "players")
public class Player extends BaseEntity {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private BigDecimal balance = new BigDecimal("0.0");
}
