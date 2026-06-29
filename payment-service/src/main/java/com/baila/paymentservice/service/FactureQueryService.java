package com.baila.paymentservice.service;

import com.baila.paymentservice.dto.response.FactureResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * Consultation des factures (lecture seule). Responsabilité unique : interrogation.
 */
public interface FactureQueryService {

    /** Factures impayées du mois en cours, éventuellement filtrées par unité de paiement. */
    List<FactureResponse> getFacturesImpayeesDuMois(String walletCode, String unite);

    /** Factures impayées sur une période donnée. */
    List<FactureResponse> getFacturesImpayeesSurPeriode(String walletCode, LocalDate debut, LocalDate fin);
}
