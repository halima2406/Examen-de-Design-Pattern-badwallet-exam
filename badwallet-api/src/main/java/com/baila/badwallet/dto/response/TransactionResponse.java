package com.baila.badwallet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
