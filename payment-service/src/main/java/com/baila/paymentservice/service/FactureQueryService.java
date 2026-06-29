package com.baila.paymentservice.service;

import com.baila.paymentservice.dto.response.FactureResponse;

import java.time.LocalDate;
import java.util.List;

public interface FactureQueryService {

    List<FactureResponse> getFacturesImpayeesDuMois(String walletCode, String unite);

    List<FactureResponse> getFacturesImpayeesSurPeriode(String walletCode, LocalDate debut, LocalDate fin);
}
