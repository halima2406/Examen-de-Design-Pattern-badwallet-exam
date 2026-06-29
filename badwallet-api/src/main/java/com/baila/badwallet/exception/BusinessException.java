package com.baila.badwallet.exception;

/**
 * Levée quand une règle métier est violée (ex : doublon de téléphone).
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
