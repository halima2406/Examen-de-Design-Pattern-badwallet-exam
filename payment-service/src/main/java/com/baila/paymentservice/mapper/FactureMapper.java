package com.baila.paymentservice.mapper;

import com.baila.paymentservice.dto.response.FactureResponse;
import com.baila.paymentservice.entity.Facture;
import org.springframework.stereotype.Component;

@Component
public class FactureMapper {

    public FactureResponse toResponse(Facture facture) {
        return new FactureResponse(
                facture.getReference(),
                facture.getWalletCode(),
                facture.getServiceName(),
                facture.getUnite(),
                facture.getLibelle(),
                facture.getMontant(),
                facture.getPeriode(),
                facture.getDateEcheance(),
                facture.getStatut().name()
        );
    }
}
