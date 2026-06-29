package com.baila.badwallet.pattern.proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Représente l'enveloppe ApiResponse renvoyée par payment-service,
 * utilisée pour désérialiser ses réponses HTTP.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RemoteApiResponse<T>(
        boolean success,
        String message,
        T data,
        Object errors
) {
}
