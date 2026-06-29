package com.baila.badwallet.controller;

import com.baila.badwallet.common.ApiResponse;
import com.baila.badwallet.common.PageResponse;
import com.baila.badwallet.dto.request.CreateWalletRequest;
import com.baila.badwallet.dto.request.DepositRequest;
import com.baila.badwallet.dto.request.WithdrawRequest;
import com.baila.badwallet.dto.response.BalanceResponse;
import com.baila.badwallet.dto.response.WalletResponse;
import com.baila.badwallet.service.DepositService;
import com.baila.badwallet.service.WalletSeederService;
import com.baila.badwallet.service.WalletService;
import com.baila.badwallet.service.WithdrawalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final DepositService depositService;
    private final WithdrawalService withdrawalService;

    public WalletController(WalletSeederService walletSeederService,
                           WalletService walletService,
                           DepositService depositService,
                           WithdrawalService withdrawalService) {
        this.walletSeederService = walletSeederService;
        this.walletService = walletService;
        this.depositService = depositService;
        this.withdrawalService = withdrawalService;
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

    /** 1.3 - Lister les portefeuilles (paginé). */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<WalletResponse>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<WalletResponse> wallets = walletService.listWallets(page, size);
        return ResponseEntity.ok(ApiResponse.success("Liste des portefeuilles récupérée", wallets));
    }

    /** 1.4 - Consulter un portefeuille par numéro de téléphone. */
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<ApiResponse<WalletResponse>> getByPhone(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(
                ApiResponse.success("Portefeuille récupéré", walletService.getByPhone(phoneNumber)));
    }

    /** 1.5 - Consulter uniquement le solde à jour. */
    @GetMapping("/{phoneNumber}/balance")
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalance(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(
                ApiResponse.success("Solde récupéré", walletService.getBalance(phoneNumber)));
    }

    /** 1.6 - Effectuer un dépôt. */
    @PostMapping("/{id}/deposit")
    public ResponseEntity<ApiResponse<WalletResponse>> deposit(
            @PathVariable Long id,
            @Valid @RequestBody DepositRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Dépôt effectué avec succès", depositService.deposit(id, request)));
    }

    /** 1.7 - Effectuer un retrait (frais de 1% plafonnés à 5000 XOF). */
    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<WalletResponse>> withdraw(
            @Valid @RequestBody WithdrawRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Retrait effectué avec succès", withdrawalService.withdraw(request)));
    }
}
