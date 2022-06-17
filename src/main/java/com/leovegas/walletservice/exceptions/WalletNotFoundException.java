package com.leovegas.walletservice.exceptions;

public class WalletNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -8347581908108933533L;

    private final long userId;

    public WalletNotFoundException(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getMessage() {
        return String.format("Unable find wallet by user id %s", this.userId);
    }
}
