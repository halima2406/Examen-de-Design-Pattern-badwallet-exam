package com.baila.badwallet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Vue d'une transaction dans l'historique.
 */
public record TransactionResponse(
        String reference,
        String type,
        BigDecimal montant,
        BigDecimal frais,
        String paymentMethod,
        String statut,
        String contrepartie,
        String description,
        LocalDateTime createdAt
) {
}
