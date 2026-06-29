package com.baila.badwallet.service;

/**
 * Initialisation asynchrone de la base avec des portefeuilles et leurs événements.
 */
public interface WalletSeederService {

    /**
     * Crée {@code numWallets} portefeuilles, chacun avec {@code eventsPerWallet} transactions.
     * Exécuté de façon asynchrone : l'appelant n'attend pas la fin du traitement.
     */
    void seedAsync(int numWallets, int eventsPerWallet);
}
