package com.baila.badwallet.service;

public interface WalletSeederService {

    void seedAsync(int numWallets, int eventsPerWallet);
}
