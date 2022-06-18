package com.leovegas.walletservice.exceptions;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    private static final long serialVersionUID = -3509024973248669992L;

    private final Long userId;
    private final BigDecimal amount;

    public InsufficientFundsException(Long userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
    }

    @Override
    public String getMessage() {
        return String.format("Unable credit %s from user id %s wallet. Insufficient funds", this.amount, this.userId);
    }
}
