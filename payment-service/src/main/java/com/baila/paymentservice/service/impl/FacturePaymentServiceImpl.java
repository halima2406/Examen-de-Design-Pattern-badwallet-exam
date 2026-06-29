package com.baila.paymentservice.service.impl;

import com.baila.paymentservice.dto.response.PaiementRecuResponse;
import com.baila.paymentservice.entity.Facture;
import com.baila.paymentservice.entity.enums.FactureStatus;
import com.baila.paymentservice.exception.BusinessException;
import com.baila.paymentservice.exception.ResourceNotFoundException;
import com.baila.paymentservice.repository.FactureRepository;
import com.baila.paymentservice.service.FacturePaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FacturePaymentServiceImpl implements FacturePaymentService {

    private final FactureRepository factureRepository;

    public FacturePaymentServiceImpl(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }

    @Override
    public PaiementRecuResponse payerFacturesDuMois(String walletCode, String serviceName) {
        LocalDate debutDuMois = LocalDate.now().withDayOfMonth(1);
        List<Facture> factures = factureRepository
                .findByWalletCodeAndServiceNameAndStatutAndPeriode(
                        walletCode, serviceName, FactureStatus.IMPAYEE, debutDuMois);

        if (factures.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Aucune facture impayée ce mois-ci pour " + serviceName
                            + " (portefeuille " + walletCode + ")");
        }
        return reglerEtConstruireRecu(walletCode, serviceName, factures);
    }

    @Override
    public PaiementRecuResponse payerFactures(String walletCode, List<String> references) {
        List<Facture> factures = references.stream()
                .map(ref -> factureRepository.findByReference(ref)
                        .orElseThrow(() -> new ResourceNotFoundException("Facture introuvable : " + ref)))
                .toList();

        for (Facture facture : factures) {
            if (!facture.getWalletCode().equals(walletCode)) {
                throw new BusinessException("La facture " + facture.getReference()
                        + " n'appartient pas au portefeuille " + walletCode);
            }
            if (!facture.estImpayee()) {
                throw new BusinessException("La facture " + facture.getReference() + " est déjà payée");
            }
        }

        String serviceName = factures.get(0).getServiceName();
        return reglerEtConstruireRecu(walletCode, serviceName, factures);
    }

    private PaiementRecuResponse reglerEtConstruireRecu(String walletCode, String serviceName, List<Facture> factures) {
        LocalDateTime maintenant = LocalDateTime.now();
        BigDecimal total = BigDecimal.ZERO;
        for (Facture facture : factures) {
            facture.setStatut(FactureStatus.PAYEE);
            facture.setDatePaiement(maintenant);
            total = total.add(facture.getMontant());
        }
        factureRepository.saveAll(factures);

        return new PaiementRecuResponse(
                walletCode,
                serviceName,
                total,
                factures.size(),
                factures.stream().map(Facture::getReference).toList(),
                maintenant);
    }
}
