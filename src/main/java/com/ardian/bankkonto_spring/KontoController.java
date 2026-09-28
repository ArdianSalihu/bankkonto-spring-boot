package com.ardian.bankkonto_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController // Diese Klasse beantwortet Web-Anfragen
@RequestMapping("/konto") // Alle Endpunkte hier starten mit /konto
public class KontoController {

    @Autowired // Spring Boot erstellt und liefert automatisch ein KontoRepository
    private KontoRepository kontoRepository;

    @GetMapping // Reagiert auf GET-Anfragen zu /konto (Daten abrufen)
    public List<Konto> alleKontenAnzeigen() {
        return kontoRepository.findAll(); // Gibt alle gespeicherten Konten zurück
    }

    // Wandelt die gesendeten JSON-Daten in ein Konto-Objekt um und speichert es
    @PostMapping // Reagiert auf POST-Anfragen zu /konto (neue Daten erstellen)
    public Konto kontoErstellen(@RequestBody Konto konto) {
        if (konto.getKontostand() < 0 ) {
            throw new IllegalArgumentException("Der Startkontostand darf nicht negativ sein!");
        }
        return kontoRepository.save(konto);
    }

    // Findet ein bestehendes Konto per ID, aktualisiert seinen Kontostand und speichert es anschließend in die Datenbank
    @PutMapping("/{id}/einzahlen")
    public Konto einzahlen(@PathVariable Long id, @RequestBody int betrag) {
        Konto konto = kontoRepository.findById(id).orElseThrow();
        konto.setKontostand(konto.getKontostand() + betrag);
        return kontoRepository.save(konto);
    }

    /*
    Findet ein bestehendes Konto per ID, guckt, ob das Konto genügend gedeckt ist, wenn ja,
    wird der gewünschte Betrag abgehoben und anschließend in der Datenbank gespeichert
     */
    @PutMapping("/{id}/abheben")
    public Konto abheben(@PathVariable Long id, @RequestBody int betrag) {
        Konto konto = kontoRepository.findById(id).orElseThrow();
        if (konto.getKontostand() < betrag) {
            throw new IllegalArgumentException("Dein Guthaben ist nicht ausreichend!");
        }
        konto.setKontostand(konto.getKontostand() - betrag);
        return kontoRepository.save(konto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
