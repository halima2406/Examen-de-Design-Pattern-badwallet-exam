package com.baila.paymentservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de réponse exposé au client (badwallet-api). N'expose pas l'entité JPA directement.
 */
public record FactureResponse(
        String reference,
        String walletCode,
        String serviceName,
        String unite,
        String libelle,
        BigDecimal montant,
        LocalDate periode,
        LocalDate dateEcheance,
        String statut
) {
}
