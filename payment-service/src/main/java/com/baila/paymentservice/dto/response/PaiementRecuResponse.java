package com.baila.paymentservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PaiementRecuResponse(
        String walletCode,
        String serviceName,
        BigDecimal montantTotal,
        int nombreFactures,
        List<String> referencesPayees,
        LocalDateTime datePaiement
) {
}
