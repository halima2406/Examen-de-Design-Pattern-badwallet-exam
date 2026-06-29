package com.baila.badwallet.controller;

import com.baila.badwallet.common.ApiResponse;
import com.baila.badwallet.service.WalletSeederService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Point d'entrée HTTP des portefeuilles. Le controller ne porte aucune logique
 * métier : il valide l'entrée et délègue à des services dédiés (un par opération).
 */
@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletSeederService walletSeederService;

    public WalletController(WalletSeederService walletSeederService) {
        this.walletSeederService = walletSeederService;
    }

    /** 1.1 - Seeder la base (asynchrone). */
    @PostMapping("/seed")
    public ResponseEntity<ApiResponse<Void>> seed(
            @RequestParam(defaultValue = "10") int numWallets,
            @RequestParam(defaultValue = "100") int eventsPerWallet) {
        walletSeederService.seedAsync(numWallets, eventsPerWallet);
        return ResponseEntity.accepted()
                .body(ApiResponse.success(
                        "Seeding lancé : " + numWallets + " portefeuilles x "
                                + eventsPerWallet + " événements", null));
    }
}
