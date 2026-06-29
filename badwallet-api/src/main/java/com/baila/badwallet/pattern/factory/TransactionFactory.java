package com.baila.badwallet.pattern.factory;

import com.baila.badwallet.entity.Transaction;
import com.baila.badwallet.entity.Wallet;
import com.baila.badwallet.entity.enums.PaymentMethod;
import com.baila.badwallet.entity.enums.TransactionStatus;
import com.baila.badwallet.entity.enums.TransactionType;
import com.baila.badwallet.pattern.singleton.TransactionReferenceGenerator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TransactionFactory {

    private final TransactionReferenceGenerator referenceGenerator =
            TransactionReferenceGenerator.getInstance();

    public Transaction createDepot(Wallet wallet, BigDecimal montant, PaymentMethod paymentMethod) {
        return baseBuilder(wallet, TransactionType.DEPOT, montant, BigDecimal.ZERO)
                .paymentMethod(paymentMethod)
                .description("Dépôt via " + paymentMethod)
                .build();
    }

    public Transaction createRetrait(Wallet wallet, BigDecimal montant, BigDecimal frais) {
        return baseBuilder(wallet, TransactionType.RETRAIT, montant, frais)
                .description("Retrait (frais: " + frais + ")")
                .build();
    }

    public Transaction createTransfertEnvoye(Wallet emetteur, Wallet destinataire, BigDecimal montant) {
        return baseBuilder(emetteur, TransactionType.TRANSFERT_ENVOYE, montant, BigDecimal.ZERO)
                .contrepartie(destinataire.getPhoneNumber())
                .description("Transfert vers " + destinataire.getPhoneNumber())
                .build();
    }

    public Transaction createTransfertRecu(Wallet destinataire, Wallet emetteur, BigDecimal montant) {
        return baseBuilder(destinataire, TransactionType.TRANSFERT_RECU, montant, BigDecimal.ZERO)
                .contrepartie(emetteur.getPhoneNumber())
                .description("Transfert reçu de " + emetteur.getPhoneNumber())
                .build();
    }

    public Transaction createPaiementFacture(Wallet wallet, BigDecimal montant, String serviceName, String description) {
        return baseBuilder(wallet, TransactionType.PAIEMENT_FACTURE, montant, BigDecimal.ZERO)
                .contrepartie(serviceName)
                .description(description)
                .build();
    }

    private Transaction.TransactionBuilder baseBuilder(Wallet wallet, TransactionType type,
                                                       BigDecimal montant, BigDecimal frais) {
        return Transaction.builder()
                .reference(referenceGenerator.next())
                .type(type)
                .montant(montant)
                .frais(frais)
                .statut(TransactionStatus.REUSSIE)
                .createdAt(LocalDateTime.now())
                .wallet(wallet);
    }
}
