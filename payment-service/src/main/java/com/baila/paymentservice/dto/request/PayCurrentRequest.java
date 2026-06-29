package com.baila.paymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Demande de paiement des factures impayées du mois en cours pour un service donné.
 */
public record PayCurrentRequest(
        @NotBlank(message = "Le nom du service est obligatoire")
        String serviceName
) {
}
