package com.baila.paymentservice.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * Demande de paiement de factures précises identifiées par leurs références.
 */
public record PayFacturesRequest(
        @NotEmpty(message = "La liste des références de factures est obligatoire")
        List<String> factureReferences
) {
}
