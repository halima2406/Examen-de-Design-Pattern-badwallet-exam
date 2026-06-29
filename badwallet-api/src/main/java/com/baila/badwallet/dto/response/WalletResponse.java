package com.baila.badwallet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
