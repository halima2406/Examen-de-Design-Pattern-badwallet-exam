package com.baila.badwallet.exception;

/**
 * Levée quand le solde d'un portefeuille est insuffisant pour une opération.
 */
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
