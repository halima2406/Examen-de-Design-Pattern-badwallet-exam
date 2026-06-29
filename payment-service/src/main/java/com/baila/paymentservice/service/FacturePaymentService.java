package com.baila.paymentservice.service;

import com.baila.paymentservice.dto.response.PaiementRecuResponse;

import java.util.List;

/**
 * Paiement des factures (écriture). Responsabilité unique : règlement.
 */
public interface FacturePaymentService {

    /** Paie toutes les factures impayées du mois en cours pour un service donné. */
    PaiementRecuResponse payerFacturesDuMois(String walletCode, String serviceName);

    /** Paie les factures précises identifiées par leurs références. */
    PaiementRecuResponse payerFactures(String walletCode, List<String> references);
}
