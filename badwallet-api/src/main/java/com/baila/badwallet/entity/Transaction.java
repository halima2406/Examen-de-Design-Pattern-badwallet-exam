package com.baila.badwallet.entity;

import com.baila.badwallet.entity.enums.PaymentMethod;
import com.baila.badwallet.entity.enums.TransactionStatus;
import com.baila.badwallet.entity.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Mouvement (événement) appliqué à un portefeuille : dépôt, retrait, transfert, paiement.
 * Le solde du portefeuille est la conséquence de la suite de ces transactions.
 */
@Entity
@Table(name = "transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Référence unique générée par le singleton TransactionReferenceGenerator. */
    @Column(nullable = false, unique = true)
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private BigDecimal montant;

    /** Frais éventuels (ex : retrait). Zéro pour les opérations sans frais. */
    @Column(nullable = false)
    private BigDecimal frais;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus statut;

    /** Contrepartie éventuelle (téléphone destinataire, nom du service de facturation...). */
    private String contrepartie;

    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Côté propriétaire de la relation : porte la clé étrangère wallet_id.
     */
    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;
}
