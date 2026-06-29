package com.baila.badwallet.dto.request;

import com.baila.badwallet.validation.ValidTelephone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Données pour payer la facture du mois en cours d'un service (ISM, WOYAFAL...).
 */
public record PayBillRequest(

        @ValidTelephone
        String phoneNumber,

        @NotBlank(message = "Le nom du service est obligatoire")
        String serviceName,

        @NotNull(message = "Le montant est obligatoire")
        @Positive(message = "Le montant doit être strictement positif")
        BigDecimal amount
) {
}
