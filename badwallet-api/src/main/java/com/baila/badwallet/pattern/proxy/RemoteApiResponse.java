package com.baila.badwallet.pattern.proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RemoteApiResponse<T>(
        boolean success,
        String message,
        T data,
        Object errors
) {
}
