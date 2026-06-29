package com.baila.paymentservice.controller;

import com.baila.paymentservice.common.ApiResponse;
import com.baila.paymentservice.dto.request.PayCurrentRequest;
import com.baila.paymentservice.dto.request.PayFacturesRequest;
import com.baila.paymentservice.dto.response.FactureResponse;
import com.baila.paymentservice.dto.response.PaiementRecuResponse;
import com.baila.paymentservice.service.FacturePaymentService;
import com.baila.paymentservice.service.FactureQueryService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * API publique du service de facturation, consommée par badwallet-api.
 * Le controller ne contient pas de logique métier : il délègue aux services.
 */
@RestController
@RequestMapping("/api/factures")
public class FactureController {

    private final FactureQueryService factureQueryService;
    private final FacturePaymentService facturePaymentService;

    public FactureController(FactureQueryService factureQueryService,
                            FacturePaymentService facturePaymentService) {
        this.factureQueryService = factureQueryService;
        this.facturePaymentService = facturePaymentService;
    }

    @GetMapping("/{walletCode}/current")
    public ResponseEntity<ApiResponse<List<FactureResponse>>> facturesDuMois(
            @PathVariable String walletCode,
            @RequestParam(required = false) String unite) {
        List<FactureResponse> factures =
                factureQueryService.getFacturesImpayeesDuMois(walletCode, unite);
        return ResponseEntity.ok(
                ApiResponse.success("Factures impayées du mois récupérées", factures));
    }

    @GetMapping("/{walletCode}/periode")
    public ResponseEntity<ApiResponse<List<FactureResponse>>> facturesSurPeriode(
            @PathVariable String walletCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        List<FactureResponse> factures =
                factureQueryService.getFacturesImpayeesSurPeriode(walletCode, debut, fin);
        return ResponseEntity.ok(
                ApiResponse.success("Factures impayées de la période récupérées", factures));
    }

    @PostMapping("/{walletCode}/pay-current")
    public ResponseEntity<ApiResponse<PaiementRecuResponse>> payerFacturesDuMois(
            @PathVariable String walletCode,
            @Valid @RequestBody PayCurrentRequest request) {
        PaiementRecuResponse recu =
                facturePaymentService.payerFacturesDuMois(walletCode, request.serviceName());
        return ResponseEntity.ok(ApiResponse.success("Factures du mois payées", recu));
    }

    @PostMapping("/{walletCode}/pay")
    public ResponseEntity<ApiResponse<PaiementRecuResponse>> payerFactures(
            @PathVariable String walletCode,
            @Valid @RequestBody PayFacturesRequest request) {
        PaiementRecuResponse recu =
                facturePaymentService.payerFactures(walletCode, request.factureReferences());
        return ResponseEntity.ok(ApiResponse.success("Factures payées", recu));
    }
}
