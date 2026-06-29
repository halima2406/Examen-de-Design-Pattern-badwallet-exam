package com.baila.badwallet.service;

import com.baila.badwallet.dto.request.WithdrawRequest;
import com.baila.badwallet.dto.response.WalletResponse;

/**
 * Opération de retrait sur un portefeuille.
 */
public interface WithdrawalService {

    WalletResponse withdraw(WithdrawRequest request);
}
