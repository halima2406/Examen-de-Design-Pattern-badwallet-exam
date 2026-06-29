package com.baila.paymentservice.exception;

/**
 * Levée quand une règle métier est violée (ex : facture déjà payée).
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
