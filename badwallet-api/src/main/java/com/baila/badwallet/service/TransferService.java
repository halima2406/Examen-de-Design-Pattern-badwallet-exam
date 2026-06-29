package com.baila.badwallet.service;

import com.baila.badwallet.dto.request.TransferRequest;
import com.baila.badwallet.dto.response.WalletResponse;

public interface TransferService {

    WalletResponse transfer(TransferRequest request);
}
