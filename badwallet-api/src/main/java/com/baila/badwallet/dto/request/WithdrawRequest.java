package com.baila.badwallet.dto.request;

import com.baila.badwallet.validation.ValidTelephone;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WithdrawRequest(

        @ValidTelephone
        String phoneNumber,

        @NotNull(message = "Le montant est obligatoire")
        @Positive(message = "Le montant doit être strictement positif")
        BigDecimal amount
) {
}
