package com.baila.paymentservice.entity;

import com.baila.paymentservice.entity.enums.FactureStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Représente une facture émise pour un portefeuille par un fournisseur de service
 * (ex : ISM pour les frais de scolarité, WOYAFAL pour l'électricité prépayée).
 */
@Entity
@Table(name = "factures")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Référence métier unique, ex : FAC-ISM-3-1. */
    @Column(nullable = false, unique = true)
    private String reference;

    /** Code du portefeuille concerné, ex : WLT-0000003. */
    @Column(nullable = false)
    private String walletCode;

    /** Fournisseur de service, ex : ISM, WOYAFAL. */
    @Column(nullable = false)
    private String serviceName;

    /** Unité de paiement, ex : WOYAFAL, MENSUALITE, INSCRIPTION. */
    @Column(nullable = false)
    private String unite;

    /** Libellé lisible de la facture. */
    @Column(nullable = false)
    private String libelle;

    @Column(nullable = false)
    private BigDecimal montant;

    /** Premier jour du mois concerné par la facture. */
    @Column(nullable = false)
    private LocalDate periode;

    @Column(nullable = false)
    private LocalDate dateEcheance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FactureStatus statut;

    private LocalDateTime datePaiement;

    public boolean estImpayee() {
        return statut == FactureStatus.IMPAYEE;
    }
}
