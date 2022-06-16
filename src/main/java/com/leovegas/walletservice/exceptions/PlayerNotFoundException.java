package com.leovegas.walletservice.exceptions;

public class PlayerNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -8347581908108933533L;

    private final long id;

    public PlayerNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Unable find player by id %s", this.id);
    }
}
