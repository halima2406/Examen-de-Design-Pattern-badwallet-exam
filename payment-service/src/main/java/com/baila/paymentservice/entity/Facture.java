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

    @Column(nullable = false, unique = true)
    private String reference;

    @Column(nullable = false)
    private String walletCode;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private String unite;

    @Column(nullable = false)
    private String libelle;

    @Column(nullable = false)
    private BigDecimal montant;

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
