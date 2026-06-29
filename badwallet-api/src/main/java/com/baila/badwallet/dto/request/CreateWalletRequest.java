package com.baila.badwallet.dto.request;

import com.baila.badwallet.entity.enums.Currency;
import com.baila.badwallet.validation.ValidTelephone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * Données de création d'un portefeuille.
 */
public record CreateWalletRequest(

        @ValidTelephone
        String phoneNumber,

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "L'email est invalide")
        String email,

        @NotNull(message = "Le solde initial est obligatoire")
        @PositiveOrZero(message = "Le solde initial doit être positif ou nul")
        BigDecimal initialBalance,

        @NotBlank(message = "Le code du portefeuille est obligatoire")
        String code,

        @NotNull(message = "La devise est obligatoire")
        Currency currency
) {
}
