package com.baila.badwallet.service;

import com.baila.badwallet.common.PageResponse;
import com.baila.badwallet.dto.request.CreateWalletRequest;
import com.baila.badwallet.dto.response.BalanceResponse;
import com.baila.badwallet.dto.response.WalletResponse;

public interface WalletService {

    WalletResponse createWallet(CreateWalletRequest request);

    PageResponse<WalletResponse> listWallets(int page, int size);

    WalletResponse getByPhone(String phoneNumber);

    BalanceResponse getBalance(String phoneNumber);
}
