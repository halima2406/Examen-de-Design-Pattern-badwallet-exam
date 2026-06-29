package com.baila.badwallet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Reçu de paiement de facture(s) renvoyé au client après débit du portefeuille.
 */
public record PaymentReceiptResponse(
        String phoneNumber,
        String serviceName,
        BigDecimal montantPaye,
        BigDecimal soldeRestant,
        int nombreFactures,
        List<String> referencesPayees,
        String referenceTransaction,
        LocalDateTime datePaiement
) {
}
