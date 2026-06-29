package com.baila.badwallet.service.impl;

import com.baila.badwallet.dto.response.TransactionResponse;
import com.baila.badwallet.mapper.TransactionMapper;
import com.baila.badwallet.repository.TransactionRepository;
import com.baila.badwallet.service.TransactionHistoryService;
import com.baila.badwallet.service.WalletFinder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Historique : retourne les transactions d'un portefeuille (les plus récentes d'abord).
 */
@Service
@Transactional(readOnly = true)
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final WalletFinder walletFinder;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionHistoryServiceImpl(WalletFinder walletFinder,
                                         TransactionRepository transactionRepository,
                                         TransactionMapper transactionMapper) {
        this.walletFinder = walletFinder;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<TransactionResponse> getHistory(String phoneNumber) {
        // Vérifie l'existence du portefeuille (lève une 404 sinon).
        walletFinder.byPhone(phoneNumber);
        return transactionRepository
                .findByWalletPhoneNumberOrderByCreatedAtDesc(phoneNumber)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
    }
}
