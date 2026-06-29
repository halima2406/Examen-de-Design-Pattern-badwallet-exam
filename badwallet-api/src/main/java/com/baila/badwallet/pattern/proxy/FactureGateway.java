package com.baila.badwallet.pattern.proxy;

import com.baila.badwallet.dto.response.FactureResponse;

import java.time.LocalDate;
import java.util.List;

public interface FactureGateway {

    List<FactureResponse> getFacturesDuMois(String walletCode, String unite);

    List<FactureResponse> getFacturesSurPeriode(String walletCode, LocalDate debut, LocalDate fin);

    FacturePaiementResultat payerFacturesDuMois(String walletCode, String serviceName);

    FacturePaiementResultat payerFactures(String walletCode, List<String> references);
}
