package com.ardian.bankkonto_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

@RestController // Diese Klasse beantwortet Web-Anfragen
@RequestMapping("/konto") // Alle Endpunkte hier starten mit /konto
public class KontoController {

    @Autowired // Spring Boot erstellt und liefert automatisch ein KontoRepository
    private KontoRepository kontoRepository;

    @Autowired
    private NutzerRepository nutzerRepository;

    private Nutzer getEingeloggterNutzer(Authentication authentication) {
        return nutzerRepository.findByUsername(authentication.getName());
    }

    // Findet den eingeloggten Nutzer und gibt nur dessen eigene Konten zurück
    @GetMapping // Reagiert auf GET-Anfragen zu /konto (Daten abrufen)
    public List<Konto> alleKontenAnzeigen(Authentication authentication) {
        Nutzer eingeloggterNutzer = getEingeloggterNutzer(authentication);
        return kontoRepository.findByNutzer(eingeloggterNutzer);
    }

    // Wandelt die gesendeten JSON-Daten in ein Konto-Objekt um, ordnet das Konto dem eingeloggten Nutzer zu und speichert es
    @PostMapping // Reagiert auf POST-Anfragen zu /konto (neue Daten erstellen)
    public Konto kontoErstellen(@RequestBody Konto konto, Authentication authentication) {
        if (konto.getKontostand() < 0 ) {
            throw new IllegalArgumentException("Der Startkontostand darf nicht negativ sein!");
        }
        Nutzer eingeloggterNutzer = getEingeloggterNutzer(authentication);
        konto.setNutzer(eingeloggterNutzer);
        return kontoRepository.save(konto);
    }

    /*
    Findet ein bestehendes Konto per ID, prüft, ob es dem eingeloggten Nutzer gehört,
    aktualisiert anschließend den Kontostand und speichert das Konto in der Datenbank
     */
    @PutMapping("/{id}/einzahlen")
    public Konto einzahlen(@PathVariable Long id, @RequestBody int betrag, Authentication authentication) {
        Konto konto = kontoRepository.findById(id).orElseThrow();
        Nutzer eingeloggterNutzer = getEingeloggterNutzer(authentication);
        if (konto.getNutzer() == null || !konto.getNutzer().equals(eingeloggterNutzer)) {
            throw new AccessDeniedException("Dieses Konto gehört Ihnen nicht!");
        }
        konto.setKontostand(konto.getKontostand() + betrag);
        return kontoRepository.save(konto);
    }

    /*
    Findet ein bestehendes Konto per ID, prüft, ob das Konto dem eingeloggten Nutzer gehört und guckt,
    ob das Konto genügend gedeckt ist, wenn ja, wird der gewünschte Betrag abgehoben und anschließend in der Datenbank gespeichert
     */
    @PutMapping("/{id}/abheben")
    public Konto abheben(@PathVariable Long id, @RequestBody int betrag, Authentication authentication) {
        Konto konto = kontoRepository.findById(id).orElseThrow();
        Nutzer eingeloggterNutzer = getEingeloggterNutzer(authentication);
        if (konto.getNutzer() == null || !konto.getNutzer().equals(eingeloggterNutzer)) {
            throw new AccessDeniedException("Dieses Konto gehört Ihnen nicht!");
        }
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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }
}
