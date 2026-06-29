package com.baila.badwallet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Vue d'une facture renvoyée par le Proxy API (proxifiée depuis payment-service).
 */
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
