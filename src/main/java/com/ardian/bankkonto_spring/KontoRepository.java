package com.ardian.bankkonto_spring;

import org.springframework.data.jpa.repository.JpaRepository;

// Leeres Interface - Spring Boot generiert automatisch fertige Methoden
// wie save(), findAll(), findById(), delete() für die Konto-Entity
public interface KontoRepository extends JpaRepository<Konto, Long> {

}
