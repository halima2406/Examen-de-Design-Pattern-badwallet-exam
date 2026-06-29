package com.baila.paymentservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Reçu retourné après le paiement d'une ou plusieurs factures.
 */
public record PaiementRecuResponse(
        String walletCode,
        String serviceName,
        BigDecimal montantTotal,
        int nombreFactures,
        List<String> referencesPayees,
        LocalDateTime datePaiement
) {
}
