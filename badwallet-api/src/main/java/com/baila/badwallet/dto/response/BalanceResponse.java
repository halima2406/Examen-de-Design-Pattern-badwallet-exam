package com.baila.badwallet.dto.response;

import java.math.BigDecimal;

public record BalanceResponse(
        String phoneNumber,
        BigDecimal balance,
        String currency
) {
}
