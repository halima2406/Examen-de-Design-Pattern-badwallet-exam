package com.baila.badwallet.exception;

/**
 * Levée quand une ressource (portefeuille, transaction...) est introuvable.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
