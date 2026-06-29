package com.baila.paymentservice.exception;

/**
 * Levée quand une ressource (ex : facture) est introuvable.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
