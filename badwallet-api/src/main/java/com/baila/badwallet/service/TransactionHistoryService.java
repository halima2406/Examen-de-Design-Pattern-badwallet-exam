package com.baila.badwallet.service;

import com.baila.badwallet.dto.response.TransactionResponse;

import java.util.List;

/**
 * Consultation de l'historique des transactions d'un portefeuille.
 */
public interface TransactionHistoryService {

    List<TransactionResponse> getHistory(String phoneNumber);
}
