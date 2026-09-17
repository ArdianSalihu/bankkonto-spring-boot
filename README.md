# Bankkonto Backend (Spring Boot)

Ein einfaches Bankkonto-Backend, entwickelt mit Spring Boot, Spring Data JPA und MySQL. Bietet eine REST-API zum Erstellen von Konten sowie zum Ein- und Auszahlen von Geld.

## Verwendete Konzepte

- Spring Boot (REST-Controller, Dependency Injection)
- Spring Data JPA (automatische Datenbank-Anbindung über Repositories)
- MySQL als relationale Datenbank
- Validierung und zentrale Fehlerbehandlung (@ExceptionHandler)
- REST-Prinzipien (GET, POST, PUT)

## Endpunkte

- `GET /konto` – Alle Konten anzeigen
- `POST /konto` – Neues Konto erstellen
- `PUT /konto/{id}/einzahlen` – Geld einzahlen
- `PUT /konto/{id}/abheben` – Geld abheben (mit Validierung gegen Überziehung)

## Setup

1. MySQL installieren und eine Datenbank namens `bankkonto_db` erstellen
2. `application.properties.example` zu `application.properties` kopieren
3. Eigenes MySQL-Passwort in `application.properties` eintragen
4. Projekt über `BankkontoSpringApplication` starten

## Testen

Die Endpunkte können z. B. mit Postman getestet werden.