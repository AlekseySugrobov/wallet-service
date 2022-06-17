package com.leovegas.walletservice.exceptions;

public class TransactionIdAlreadyExists extends RuntimeException {
    private static final long serialVersionUID = -6400133030810725329L;

    private final String transactionId;

    public TransactionIdAlreadyExists(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String getMessage() {
        return String.format("Transaction with id %s already exists", this.transactionId);
    }
}
