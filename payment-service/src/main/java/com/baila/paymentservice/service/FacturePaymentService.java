package com.baila.paymentservice.service;

import com.baila.paymentservice.dto.response.PaiementRecuResponse;

import java.util.List;

public interface FacturePaymentService {

    PaiementRecuResponse payerFacturesDuMois(String walletCode, String serviceName);

    PaiementRecuResponse payerFactures(String walletCode, List<String> references);
}
