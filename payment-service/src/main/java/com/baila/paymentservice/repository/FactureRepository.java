package com.baila.paymentservice.repository;

import com.baila.paymentservice.entity.Facture;
import com.baila.paymentservice.entity.enums.FactureStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FactureRepository extends JpaRepository<Facture, Long> {

    Optional<Facture> findByReference(String reference);

    List<Facture> findByWalletCodeAndStatutAndPeriode(
            String walletCode, FactureStatus statut, LocalDate periode);

    List<Facture> findByWalletCodeAndUniteAndStatutAndPeriode(
            String walletCode, String unite, FactureStatus statut, LocalDate periode);

    List<Facture> findByWalletCodeAndStatutAndPeriodeBetween(
            String walletCode, FactureStatus statut, LocalDate debut, LocalDate fin);

    List<Facture> findByWalletCodeAndServiceNameAndStatutAndPeriode(
            String walletCode, String serviceName, FactureStatus statut, LocalDate periode);

    boolean existsByWalletCode(String walletCode);
}
