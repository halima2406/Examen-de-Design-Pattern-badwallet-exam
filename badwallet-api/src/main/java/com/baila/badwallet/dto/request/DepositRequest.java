package com.baila.badwallet.dto.request;

import com.baila.badwallet.entity.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Données d'un dépôt sur un portefeuille.
 */
public record DepositRequest(

        @NotNull(message = "Le montant est obligatoire")
        @Positive(message = "Le montant doit être strictement positif")
        BigDecimal amount,

        @NotNull(message = "Le moyen de paiement est obligatoire")
        PaymentMethod paymentMethod
) {
}
