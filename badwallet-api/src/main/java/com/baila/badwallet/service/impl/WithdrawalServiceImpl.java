package com.baila.badwallet.service.impl;

import com.baila.badwallet.dto.request.WithdrawRequest;
import com.baila.badwallet.dto.response.WalletResponse;
import com.baila.badwallet.entity.Transaction;
import com.baila.badwallet.entity.Wallet;
import com.baila.badwallet.entity.enums.TransactionType;
import com.baila.badwallet.exception.InsufficientFundsException;
import com.baila.badwallet.mapper.WalletMapper;
import com.baila.badwallet.pattern.factory.TransactionFactory;
import com.baila.badwallet.pattern.strategy.FeeStrategyResolver;
import com.baila.badwallet.repository.TransactionRepository;
import com.baila.badwallet.repository.WalletRepository;
import com.baila.badwallet.service.WalletFinder;
import com.baila.badwallet.service.WithdrawalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Retrait : applique les frais (Strategy), vérifie le solde, débite et trace.
 */
@Service
@Transactional
public class WithdrawalServiceImpl implements WithdrawalService {

    private final WalletFinder walletFinder;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionFactory transactionFactory;
    private final FeeStrategyResolver feeStrategyResolver;
    private final WalletMapper walletMapper;

    public WithdrawalServiceImpl(WalletFinder walletFinder,
                                 WalletRepository walletRepository,
                                 TransactionRepository transactionRepository,
                                 TransactionFactory transactionFactory,
                                 FeeStrategyResolver feeStrategyResolver,
                                 WalletMapper walletMapper) {
        this.walletFinder = walletFinder;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.transactionFactory = transactionFactory;
        this.feeStrategyResolver = feeStrategyResolver;
        this.walletMapper = walletMapper;
    }

    @Override
    public WalletResponse withdraw(WithdrawRequest request) {
        Wallet wallet = walletFinder.byPhone(request.phoneNumber());

        BigDecimal frais = feeStrategyResolver.resolveFee(TransactionType.RETRAIT, request.amount());
        BigDecimal montantTotal = request.amount().add(frais);

        if (wallet.getBalance().compareTo(montantTotal) < 0) {
            throw new InsufficientFundsException(
                    "Solde insuffisant : " + montantTotal + " requis (frais inclus), solde "
                            + wallet.getBalance());
        }

        wallet.setBalance(wallet.getBalance().subtract(montantTotal));

        Transaction transaction = transactionFactory.createRetrait(wallet, request.amount(), frais);
        transactionRepository.save(transaction);

        return walletMapper.toResponse(walletRepository.save(wallet));
    }
}
