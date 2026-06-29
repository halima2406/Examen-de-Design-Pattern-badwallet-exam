package com.baila.paymentservice.service.impl;

import com.baila.paymentservice.dto.response.FactureResponse;
import com.baila.paymentservice.entity.enums.FactureStatus;
import com.baila.paymentservice.exception.ResourceNotFoundException;
import com.baila.paymentservice.mapper.FactureMapper;
import com.baila.paymentservice.repository.FactureRepository;
import com.baila.paymentservice.service.FactureQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FactureQueryServiceImpl implements FactureQueryService {

    private final FactureRepository factureRepository;
    private final FactureMapper factureMapper;

    public FactureQueryServiceImpl(FactureRepository factureRepository, FactureMapper factureMapper) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
    }

    @Override
    public List<FactureResponse> getFacturesImpayeesDuMois(String walletCode, String unite) {
        verifierWalletConnu(walletCode);
        LocalDate debutDuMois = LocalDate.now().withDayOfMonth(1);

        List<com.baila.paymentservice.entity.Facture> factures = (unite == null || unite.isBlank())
                ? factureRepository.findByWalletCodeAndStatutAndPeriode(
                        walletCode, FactureStatus.IMPAYEE, debutDuMois)
                : factureRepository.findByWalletCodeAndUniteAndStatutAndPeriode(
                        walletCode, unite, FactureStatus.IMPAYEE, debutDuMois);

        return factures.stream().map(factureMapper::toResponse).toList();
    }

    @Override
    public List<FactureResponse> getFacturesImpayeesSurPeriode(String walletCode, LocalDate debut, LocalDate fin) {
        verifierWalletConnu(walletCode);
        return factureRepository
                .findByWalletCodeAndStatutAndPeriodeBetween(walletCode, FactureStatus.IMPAYEE, debut, fin)
                .stream()
                .map(factureMapper::toResponse)
                .toList();
    }

    private void verifierWalletConnu(String walletCode) {
        if (!factureRepository.existsByWalletCode(walletCode)) {
            throw new ResourceNotFoundException(
                    "Aucune facture connue pour le portefeuille " + walletCode);
        }
    }
}
