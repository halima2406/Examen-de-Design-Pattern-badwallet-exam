package com.baila.badwallet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Vue d'un portefeuille exposée au client (sans la liste complète des transactions).
 */
public record WalletResponse(
        Long id,
        String phoneNumber,
        String email,
        String code,
        BigDecimal balance,
        String currency,
        LocalDateTime createdAt
) {
}
