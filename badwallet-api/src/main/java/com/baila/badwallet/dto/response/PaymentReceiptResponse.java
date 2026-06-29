package com.baila.badwallet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
