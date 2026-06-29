package com.baila.badwallet.dto.response;

import java.math.BigDecimal;

/**
 * Vue du solde à jour d'un portefeuille.
 */
public record BalanceResponse(
        String phoneNumber,
        BigDecimal balance,
        String currency
) {
}
