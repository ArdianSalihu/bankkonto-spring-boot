package com.ardian.bankkonto_spring;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Leeres Interface - Spring Boot generiert automatisch fertige Methoden
// wie save(), findAll(), findById(), delete() für die Konto-Entity
public interface KontoRepository extends JpaRepository<Konto, Long> {
    List<Konto> findByNutzer(Nutzer nutzer);
}
