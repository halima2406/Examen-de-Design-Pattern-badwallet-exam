package com.baila.badwallet.service.impl;

import com.baila.badwallet.entity.Transaction;
import com.baila.badwallet.entity.Wallet;
import com.baila.badwallet.entity.enums.Currency;
import com.baila.badwallet.entity.enums.PaymentMethod;
import com.baila.badwallet.pattern.factory.TransactionFactory;
import com.baila.badwallet.pattern.strategy.FeeStrategyResolver;
import com.baila.badwallet.entity.enums.TransactionType;
import com.baila.badwallet.repository.WalletRepository;
import com.baila.badwallet.service.WalletSeederService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Génère des données de test (portefeuilles + événements) de façon asynchrone.
 * Le solde de chaque portefeuille découle de la suite de ses transactions.
 */
@Service
public class WalletSeederServiceImpl implements WalletSeederService {

    private static final Logger log = LoggerFactory.getLogger(WalletSeederServiceImpl.class);

    private final WalletRepository walletRepository;
    private final TransactionFactory transactionFactory;
    private final FeeStrategyResolver feeStrategyResolver;

    public WalletSeederServiceImpl(WalletRepository walletRepository,
                                   TransactionFactory transactionFactory,
                                   FeeStrategyResolver feeStrategyResolver) {
        this.walletRepository = walletRepository;
        this.transactionFactory = transactionFactory;
        this.feeStrategyResolver = feeStrategyResolver;
    }

    @Override
    @Async
    @Transactional
    public void seedAsync(int numWallets, int eventsPerWallet) {
        log.info("Seeding démarré : {} portefeuilles x {} événements", numWallets, eventsPerWallet);

        for (int i = 1; i <= numWallets; i++) {
            String phone = "+22177" + String.format("%07d", i);
            if (walletRepository.existsByPhoneNumber(phone)) {
                continue;
            }
            Wallet wallet = Wallet.builder()
                    .phoneNumber(phone)
                    .email("client" + i + "@badwallet.sn")
                    .code(String.format("WLT-%07d", i))
                    .balance(BigDecimal.ZERO)
                    .currency(Currency.XOF)
                    .build();

            genererEvenements(wallet, eventsPerWallet, new Random(i));
            walletRepository.save(wallet);
        }

        log.info("Seeding terminé.");
    }

    private void genererEvenements(Wallet wallet, int eventsPerWallet, Random rnd) {
        BigDecimal balance = BigDecimal.ZERO;

        for (int e = 0; e < eventsPerWallet; e++) {
            boolean depot = rnd.nextInt(100) < 75 || balance.compareTo(new BigDecimal("20000")) < 0;

            if (depot) {
                BigDecimal montant = BigDecimal.valueOf(2000L + rnd.nextInt(18) * 1000L);
                balance = balance.add(montant);
                Transaction tx = transactionFactory.createDepot(wallet, montant, PaymentMethod.CREDIT_CARD);
                wallet.getTransactions().add(tx);
            } else {
                BigDecimal montant = BigDecimal.valueOf(1000L + rnd.nextInt(5) * 1000L);
                BigDecimal frais = feeStrategyResolver.resolveFee(TransactionType.RETRAIT, montant);
                BigDecimal total = montant.add(frais);
                if (balance.compareTo(total) >= 0) {
                    balance = balance.subtract(total);
                    Transaction tx = transactionFactory.createRetrait(wallet, montant, frais);
                    wallet.getTransactions().add(tx);
                }
            }
        }

        wallet.setBalance(balance);
    }
}
