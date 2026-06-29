package com.baila.badwallet.service;

import com.baila.badwallet.dto.request.TransferRequest;
import com.baila.badwallet.dto.response.WalletResponse;

/**
 * Opération de transfert entre deux portefeuilles.
 */
public interface TransferService {

    /** @return le portefeuille émetteur mis à jour. */
    WalletResponse transfer(TransferRequest request);
}
