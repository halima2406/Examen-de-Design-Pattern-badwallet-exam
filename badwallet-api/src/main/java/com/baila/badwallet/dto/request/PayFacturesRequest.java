package com.baila.badwallet.dto.request;

import com.baila.badwallet.validation.ValidTelephone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PayFacturesRequest(

        @ValidTelephone
        String phoneNumber,

        @NotBlank(message = "Le nom du service est obligatoire")
        String serviceName,

        @NotEmpty(message = "La liste des références de factures est obligatoire")
        List<String> factureReferences
) {
}
