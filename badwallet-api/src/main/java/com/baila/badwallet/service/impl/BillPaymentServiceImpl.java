package com.baila.badwallet.service.impl;

import com.baila.badwallet.dto.request.PayBillRequest;
import com.baila.badwallet.dto.request.PayFacturesRequest;
import com.baila.badwallet.dto.response.PaymentReceiptResponse;
import com.baila.badwallet.entity.Transaction;
import com.baila.badwallet.entity.Wallet;
import com.baila.badwallet.exception.BusinessException;
import com.baila.badwallet.exception.InsufficientFundsException;
import com.baila.badwallet.pattern.factory.TransactionFactory;
import com.baila.badwallet.pattern.proxy.FactureGateway;
import com.baila.badwallet.pattern.proxy.FacturePaiementResultat;
import com.baila.badwallet.repository.TransactionRepository;
import com.baila.badwallet.repository.WalletRepository;
import com.baila.badwallet.service.BillPaymentService;
import com.baila.badwallet.service.WalletFinder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Paiement de factures : règle les factures côté payment-service (via le Proxy),
 * débite le portefeuille du montant dû et enregistre la transaction.
 */
@Service
@Transactional
public class BillPaymentServiceImpl implements BillPaymentService {

    private final WalletFinder walletFinder;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionFactory transactionFactory;
    private final FactureGateway factureGateway;

    public BillPaymentServiceImpl(WalletFinder walletFinder,
                                  WalletRepository walletRepository,
                                  TransactionRepository transactionRepository,
                                  TransactionFactory transactionFactory,
                                  FactureGateway factureGateway) {
        this.walletFinder = walletFinder;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.transactionFactory = transactionFactory;
        this.factureGateway = factureGateway;
    }

    @Override
    public PaymentReceiptResponse payCurrentBill(PayBillRequest request) {
        Wallet wallet = walletFinder.byPhone(request.phoneNumber());

        FacturePaiementResultat resultat =
                factureGateway.payerFacturesDuMois(wallet.getCode(), request.serviceName());
        BigDecimal montantDu = resultat.montantTotal();

        if (montantDu.compareTo(request.amount()) > 0) {
            throw new BusinessException("Le montant dû (" + montantDu
                    + ") dépasse le montant autorisé (" + request.amount() + ")");
        }
        return debiterEtTracer(wallet, request.serviceName(), montantDu, resultat);
    }

    @Override
    public PaymentReceiptResponse paySpecificFactures(PayFacturesRequest request) {
        Wallet wallet = walletFinder.byPhone(request.phoneNumber());

        FacturePaiementResultat resultat =
                factureGateway.payerFactures(wallet.getCode(), request.factureReferences());

        return debiterEtTracer(wallet, request.serviceName(), resultat.montantTotal(), resultat);
    }

    private PaymentReceiptResponse debiterEtTracer(Wallet wallet, String serviceName,
                                                   BigDecimal montant, FacturePaiementResultat resultat) {
        if (wallet.getBalance().compareTo(montant) < 0) {
            throw new InsufficientFundsException(
                    "Solde insuffisant pour payer " + montant + ", solde " + wallet.getBalance());
        }

        wallet.setBalance(wallet.getBalance().subtract(montant));

        String description = "Paiement " + serviceName + " - factures "
                + String.join(", ", resultat.referencesPayees());
        Transaction transaction = transactionFactory.createPaiementFacture(
                wallet, montant, serviceName, description);
        transactionRepository.save(transaction);
        walletRepository.save(wallet);

        return new PaymentReceiptResponse(
                wallet.getPhoneNumber(),
                serviceName,
                montant,
                wallet.getBalance(),
                resultat.nombreFactures(),
                resultat.referencesPayees(),
                transaction.getReference(),
                transaction.getCreatedAt());
    }
}
