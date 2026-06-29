package com.baila.badwallet.service.impl;

import com.baila.badwallet.dto.request.TransferRequest;
import com.baila.badwallet.dto.response.WalletResponse;
import com.baila.badwallet.entity.Wallet;
import com.baila.badwallet.exception.BusinessException;
import com.baila.badwallet.exception.InsufficientFundsException;
import com.baila.badwallet.mapper.WalletMapper;
import com.baila.badwallet.pattern.factory.TransactionFactory;
import com.baila.badwallet.repository.TransactionRepository;
import com.baila.badwallet.repository.WalletRepository;
import com.baila.badwallet.service.TransferService;
import com.baila.badwallet.service.WalletFinder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    private final WalletFinder walletFinder;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionFactory transactionFactory;
    private final WalletMapper walletMapper;

    public TransferServiceImpl(WalletFinder walletFinder,
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
    public WalletResponse transfer(TransferRequest request) {
        if (request.senderPhone().equals(request.receiverPhone())) {
            throw new BusinessException("L'émetteur et le destinataire doivent être différents");
        }

        Wallet emetteur = walletFinder.byPhone(request.senderPhone());
        Wallet destinataire = walletFinder.byPhone(request.receiverPhone());

        if (emetteur.getBalance().compareTo(request.amount()) < 0) {
            throw new InsufficientFundsException(
                    "Solde insuffisant pour le transfert : " + request.amount()
                            + " requis, solde " + emetteur.getBalance());
        }

        emetteur.setBalance(emetteur.getBalance().subtract(request.amount()));
        destinataire.setBalance(destinataire.getBalance().add(request.amount()));

        transactionRepository.save(
                transactionFactory.createTransfertEnvoye(emetteur, destinataire, request.amount()));
        transactionRepository.save(
                transactionFactory.createTransfertRecu(destinataire, emetteur, request.amount()));

        walletRepository.save(destinataire);
        return walletMapper.toResponse(walletRepository.save(emetteur));
    }
}
