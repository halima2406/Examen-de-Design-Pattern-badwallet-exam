package com.baila.badwallet.controller;

import com.baila.badwallet.common.ApiResponse;
import com.baila.badwallet.dto.request.CreateWalletRequest;
import com.baila.badwallet.dto.response.WalletResponse;
import com.baila.badwallet.service.WalletSeederService;
import com.baila.badwallet.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final WalletService walletService;

    public WalletController(WalletSeederService walletSeederService,
                           WalletService walletService) {
        this.walletSeederService = walletSeederService;
        this.walletService = walletService;
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

    /** 1.2 - Créer un portefeuille. */
    @PostMapping
    public ResponseEntity<ApiResponse<WalletResponse>> create(
            @Valid @RequestBody CreateWalletRequest request) {
        WalletResponse wallet = walletService.createWallet(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Portefeuille créé avec succès", wallet));
    }
}
