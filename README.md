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

## Authentifizierung

- Alle Endpunkte außer `POST /auth/registrieren` sind per Basic Auth geschützt
- `POST /auth/registrieren` legt einen neuen Nutzer an, das Passwort wird mit BCrypt gehasht gespeichert
- Hinweis: Konten sind aktuell noch nicht an einzelne Nutzer gebunden, jeder eingeloggte Nutzer sieht alle Konten

## Testen

Die Endpunkte können z. B. mit Postman getestet werden. Zuerst über `POST /auth/registrieren` einen Nutzer anlegen (Body: `{"username": "...", "password": "..."}`). Danach bei allen weiteren Anfragen unter **Authorization → Basic Auth** die Zugangsdaten eintragen. Ohne Login antwortet die API mit 401.