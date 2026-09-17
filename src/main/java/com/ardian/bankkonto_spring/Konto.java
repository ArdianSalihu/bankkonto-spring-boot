package com.ardian.bankkonto_spring;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Diese Klasse wird als Tabelle in der Datenbank gespeichert
public class Konto {

    @Id // Eindeutiger Bezeichner (wie eine Kontonummer)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Datenbank vergibt automatisch fortlaufende IDs
    private Long id;

    private int kontostand;

    public int getKontostand() {
        return kontostand;
    }

    public void setKontostand(int kontostand) {
        this.kontostand = kontostand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
