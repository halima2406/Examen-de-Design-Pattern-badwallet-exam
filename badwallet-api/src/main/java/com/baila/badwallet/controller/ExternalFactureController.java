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

@RestController
@RequestMapping("/api/external/factures")
public class ExternalFactureController {

    private final FactureGateway factureGateway;

    public ExternalFactureController(FactureGateway factureGateway) {
        this.factureGateway = factureGateway;
    }

    @GetMapping("/{code}/current")
    public ResponseEntity<ApiResponse<List<FactureResponse>>> current(
            @PathVariable String code,
            @RequestParam(required = false) String unite) {
        List<FactureResponse> factures = factureGateway.getFacturesDuMois(code, unite);
        return ResponseEntity.ok(
                ApiResponse.success("Factures impayées du mois récupérées", factures));
    }

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
