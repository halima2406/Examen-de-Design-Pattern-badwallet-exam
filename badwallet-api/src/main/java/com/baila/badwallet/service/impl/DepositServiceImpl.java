package com.baila.badwallet.service.impl;

import com.baila.badwallet.dto.request.DepositRequest;
import com.baila.badwallet.dto.response.WalletResponse;
import com.baila.badwallet.entity.Transaction;
import com.baila.badwallet.entity.Wallet;
import com.baila.badwallet.mapper.WalletMapper;
import com.baila.badwallet.pattern.factory.TransactionFactory;
import com.baila.badwallet.repository.TransactionRepository;
import com.baila.badwallet.repository.WalletRepository;
import com.baila.badwallet.service.DepositService;
import com.baila.badwallet.service.WalletFinder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DepositServiceImpl implements DepositService {

    private final WalletFinder walletFinder;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionFactory transactionFactory;
    private final WalletMapper walletMapper;

    public DepositServiceImpl(WalletFinder walletFinder,
                              WalletRepository walletRepository,
                              TransactionRepository transactionRepository,
                              TransactionFactory transactionFactory,
                              WalletMapper walletMapper) {
        this.walletFinder = walletFinder;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.transactionFactory = transactionFactory;
        this.walletMapper = walletMapper;
    }

    @Override
    public WalletResponse deposit(Long walletId, DepositRequest request) {
        Wallet wallet = walletFinder.byId(walletId);

        wallet.setBalance(wallet.getBalance().add(request.amount()));

        Transaction transaction = transactionFactory.createDepot(
                wallet, request.amount(), request.paymentMethod());
        transactionRepository.save(transaction);

        return walletMapper.toResponse(walletRepository.save(wallet));
    }
}
