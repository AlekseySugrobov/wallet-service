package com.leovegas.walletservice.exceptions;

public class WalletUniqueConstraintViolationException extends RuntimeException {
    private static final long serialVersionUID = 8824637203142195128L;

    private final long userId;

    public WalletUniqueConstraintViolationException(long userId) {
        this.userId = userId;
    }

    @Override
    public String getMessage() {
        return String.format("Wallet unique constraint violation for user id %s", this.userId);
    }
}
