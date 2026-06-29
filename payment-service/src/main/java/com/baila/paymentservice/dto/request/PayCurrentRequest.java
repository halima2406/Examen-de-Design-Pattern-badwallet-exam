package com.baila.paymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PayCurrentRequest(
        @NotBlank(message = "Le nom du service est obligatoire")
        String serviceName
) {
}
