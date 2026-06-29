package com.baila.paymentservice.bootstrap;

import com.baila.paymentservice.entity.Facture;
import com.baila.paymentservice.entity.enums.FactureStatus;
import com.baila.paymentservice.repository.FactureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Initialise des factures de test au démarrage (mock via CommandLineRunner).
 *
 * Pour chaque portefeuille WLT-0000001..WLT-0000010 et chaque service (ISM, WOYAFAL),
 * une facture impayée est créée pour le mois en cours et les deux mois précédents.
 * Références au format FAC-{SERVICE}-{numéro}-{index}, ex : FAC-ISM-3-1.
 */
@Component
public class FactureSeeder implements CommandLineRunner {

    private static final int NB_WALLETS = 10;
    /** 4 mois d'historique : l'index 1 = mois le plus ancien, le dernier index = mois courant. */
    private static final int NB_MOIS = 4;

    private record ServiceCatalogue(String nom, String unite, String libelle, BigDecimal montant) {
    }

    private static final List<ServiceCatalogue> SERVICES = List.of(
            new ServiceCatalogue("ISM", "MENSUALITE", "Tranche de scolarité ISM", new BigDecimal("5000.00")),
            new ServiceCatalogue("WOYAFAL", "WOYAFAL", "Recharge électricité Woyafal", new BigDecimal("10000.00"))
    );

    private final FactureRepository factureRepository;

    public FactureSeeder(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }

    @Override
    public void run(String... args) {
        if (factureRepository.count() > 0) {
            return;
        }

        List<Facture> factures = new ArrayList<>();
        LocalDate debutMoisCourant = LocalDate.now().withDayOfMonth(1);

        for (int numero = 1; numero <= NB_WALLETS; numero++) {
            String walletCode = String.format("WLT-%07d", numero);
            for (ServiceCatalogue service : SERVICES) {
                int index = 1;
                // index 1 = mois le plus ancien, dernier index = mois courant
                for (int moisEnArriere = NB_MOIS - 1; moisEnArriere >= 0; moisEnArriere--) {
                    LocalDate periode = debutMoisCourant.minusMonths(moisEnArriere);
                    factures.add(Facture.builder()
                            .reference(String.format("FAC-%s-%d-%d", service.nom(), numero, index))
                            .walletCode(walletCode)
                            .serviceName(service.nom())
                            .unite(service.unite())
                            .libelle(service.libelle() + " - " + periode.getMonth())
                            .montant(service.montant())
                            .periode(periode)
                            .dateEcheance(periode.plusDays(10))
                            .statut(FactureStatus.IMPAYEE)
                            .build());
                    index++;
                }
            }
        }

        factureRepository.saveAll(factures);
    }
}
