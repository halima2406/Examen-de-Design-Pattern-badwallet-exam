package com.baila.paymentservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FactureResponse(
        String reference,
        String walletCode,
        String serviceName,
        String unite,
        String libelle,
        BigDecimal montant,
        LocalDate periode,
        LocalDate dateEcheance,
        String statut
) {
}
