package com.baila.badwallet.service;

import com.baila.badwallet.entity.Wallet;
import com.baila.badwallet.exception.ResourceNotFoundException;
import com.baila.badwallet.repository.WalletRepository;
import org.springframework.stereotype.Component;

/**
 * Collaborateur partagé pour retrouver un portefeuille de façon cohérente.
 * Évite de dupliquer la logique de recherche (et les messages d'erreur) dans
 * chaque service métier (principe DRY + responsabilité unique).
 */
@Component
public class WalletFinder {

    private final WalletRepository walletRepository;

    public WalletFinder(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet byPhone(String phoneNumber) {
        return walletRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Portefeuille introuvable pour le numéro " + phoneNumber));
    }

    public Wallet byId(Long id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Portefeuille introuvable pour l'identifiant " + id));
    }

    public Wallet byCode(String code) {
        return walletRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Portefeuille introuvable pour le code " + code));
    }
}
