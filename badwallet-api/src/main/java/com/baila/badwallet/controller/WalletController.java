package com.baila.badwallet.controller;

import com.baila.badwallet.common.ApiResponse;
import com.baila.badwallet.common.PageResponse;
import com.baila.badwallet.dto.request.CreateWalletRequest;
import com.baila.badwallet.dto.request.DepositRequest;
import com.baila.badwallet.dto.request.PayBillRequest;
import com.baila.badwallet.dto.request.PayFacturesRequest;
import com.baila.badwallet.dto.request.TransferRequest;
import com.baila.badwallet.dto.request.WithdrawRequest;
import com.baila.badwallet.dto.response.BalanceResponse;
import com.baila.badwallet.dto.response.PaymentReceiptResponse;
import com.baila.badwallet.dto.response.TransactionResponse;
import com.baila.badwallet.dto.response.WalletResponse;
import com.baila.badwallet.service.BillPaymentService;
import com.baila.badwallet.service.DepositService;
import com.baila.badwallet.service.TransactionHistoryService;
import com.baila.badwallet.service.TransferService;
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

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletSeederService walletSeederService;
    private final WalletService walletService;
    private final DepositService depositService;
    private final WithdrawalService withdrawalService;
    private final TransferService transferService;
    private final BillPaymentService billPaymentService;
    private final TransactionHistoryService transactionHistoryService;

    public WalletController(WalletSeederService walletSeederService,
                           WalletService walletService,
                           DepositService depositService,
                           WithdrawalService withdrawalService,
                           TransferService transferService,
                           BillPaymentService billPaymentService,
                           TransactionHistoryService transactionHistoryService) {
        this.walletSeederService = walletSeederService;
        this.walletService = walletService;
        this.depositService = depositService;
        this.withdrawalService = withdrawalService;
        this.transferService = transferService;
        this.billPaymentService = billPaymentService;
        this.transactionHistoryService = transactionHistoryService;
    }

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

    @PostMapping
    public ResponseEntity<ApiResponse<WalletResponse>> create(
            @Valid @RequestBody CreateWalletRequest request) {
        WalletResponse wallet = walletService.createWallet(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Portefeuille créé avec succès", wallet));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<WalletResponse>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<WalletResponse> wallets = walletService.listWallets(page, size);
        return ResponseEntity.ok(ApiResponse.success("Liste des portefeuilles récupérée", wallets));
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<ApiResponse<WalletResponse>> getByPhone(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(
                ApiResponse.success("Portefeuille récupéré", walletService.getByPhone(phoneNumber)));
    }

    @GetMapping("/{phoneNumber}/balance")
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalance(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(
                ApiResponse.success("Solde récupéré", walletService.getBalance(phoneNumber)));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<ApiResponse<WalletResponse>> deposit(
            @PathVariable Long id,
            @Valid @RequestBody DepositRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Dépôt effectué avec succès", depositService.deposit(id, request)));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<WalletResponse>> withdraw(
            @Valid @RequestBody WithdrawRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Retrait effectué avec succès", withdrawalService.withdraw(request)));
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<WalletResponse>> transfer(
            @Valid @RequestBody TransferRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Transfert effectué avec succès", transferService.transfer(request)));
    }

    @PostMapping("/pay")
    public ResponseEntity<ApiResponse<PaymentReceiptResponse>> pay(
            @Valid @RequestBody PayBillRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Paiement effectué avec succès",
                        billPaymentService.payCurrentBill(request)));
    }

    @PostMapping("/pay-factures")
    public ResponseEntity<ApiResponse<PaymentReceiptResponse>> payFactures(
            @Valid @RequestBody PayFacturesRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Factures payées avec succès",
                        billPaymentService.paySpecificFactures(request)));
    }

    @GetMapping("/{phoneNumber}/transactions")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> transactions(
            @PathVariable String phoneNumber) {
        return ResponseEntity.ok(
                ApiResponse.success("Historique récupéré",
                        transactionHistoryService.getHistory(phoneNumber)));
    }
}
