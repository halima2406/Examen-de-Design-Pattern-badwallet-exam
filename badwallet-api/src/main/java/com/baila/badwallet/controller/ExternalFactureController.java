package com.baila.badwallet.controller;

import com.baila.badwallet.common.ApiResponse;
import com.baila.badwallet.dto.response.FactureResponse;
import com.baila.badwallet.pattern.proxy.FactureGateway;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Proxy API (Partie 2) : expose les factures du service externe payment-service
 * sans que le client n'ait à connaître ce dernier. Délègue au {@link FactureGateway}
 * (pattern Proxy), qui réalise les appels HTTP distants.
 */
@RestController
@RequestMapping("/api/external/factures")
public class ExternalFactureController {

    private final FactureGateway factureGateway;

    public ExternalFactureController(FactureGateway factureGateway) {
        this.factureGateway = factureGateway;
    }

    /** 2.2 / 2.3 - Factures impayées du mois en cours (filtre optionnel par unité). */
    @GetMapping("/{code}/current")
    public ResponseEntity<ApiResponse<List<FactureResponse>>> current(
            @PathVariable String code,
            @RequestParam(required = false) String unite) {
        List<FactureResponse> factures = factureGateway.getFacturesDuMois(code, unite);
        return ResponseEntity.ok(
                ApiResponse.success("Factures impayées du mois récupérées", factures));
    }

    /** 2.4 - Factures impayées sur une période. */
    @GetMapping("/{code}/periode")
    public ResponseEntity<ApiResponse<List<FactureResponse>>> periode(
            @PathVariable String code,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        List<FactureResponse> factures = factureGateway.getFacturesSurPeriode(code, debut, fin);
        return ResponseEntity.ok(
                ApiResponse.success("Factures impayées de la période récupérées", factures));
    }
}
