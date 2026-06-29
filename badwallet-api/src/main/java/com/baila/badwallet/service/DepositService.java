package com.baila.badwallet.service;

import com.baila.badwallet.dto.request.DepositRequest;
import com.baila.badwallet.dto.response.WalletResponse;

/**
 * Opération de dépôt sur un portefeuille.
 */
public interface DepositService {

    WalletResponse deposit(Long walletId, DepositRequest request);
}
