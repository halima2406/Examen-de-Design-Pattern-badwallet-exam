package com.baila.badwallet.pattern.proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Résultat de paiement renvoyé par payment-service après règlement de factures.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FacturePaiementResultat(
        String walletCode,
        String serviceName,
        BigDecimal montantTotal,
        int nombreFactures,
        List<String> referencesPayees,
        LocalDateTime datePaiement
) {
}
