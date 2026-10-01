# Bankkonto Backend (Spring Boot)

Ein einfaches Bankkonto-Backend, entwickelt mit Spring Boot, Spring Data JPA und MySQL. Bietet eine REST-API zum Erstellen von Konten sowie zum Ein- und Auszahlen von Geld.

## Verwendete Konzepte

- Spring Boot (REST-Controller, Dependency Injection)
- Spring Data JPA (automatische Datenbank-Anbindung über Repositories)
- Spring Security (Basic Auth, BCrypt-Passwort-Hashing)
- MySQL als relationale Datenbank
- Datenbank-Beziehungen (@ManyToOne zwischen Konto und Nutzer)
- Validierung und zentrale Fehlerbehandlung (@ExceptionHandler)
- REST-Prinzipien (GET, POST, PUT)

## Endpunkte

- `POST /auth/registrieren` – Neuen Nutzer registrieren (ohne Login erreichbar)
- `GET /konto` – Eigene Konten anzeigen
- `POST /konto` – Neues Konto erstellen (wird automatisch dem eingeloggten Nutzer zugeordnet)
- `PUT /konto/{id}/einzahlen` – Geld einzahlen
- `PUT /konto/{id}/abheben` – Geld abheben (mit Validierung gegen Überziehung)

## Setup

1. MySQL installieren und eine Datenbank namens `bankkonto_db` erstellen
2. `application.properties.example` zu `application.properties` kopieren
3. Eigenes MySQL-Passwort in `application.properties` eintragen
4. Projekt über `BankkontoSpringApplication` starten

## Authentifizierung

- Alle Endpunkte außer `POST /auth/registrieren` sind per Basic Auth geschützt
- `POST /auth/registrieren` legt einen neuen Nutzer an, das Passwort wird mit BCrypt gehasht gespeichert
- Jedes Konto gehört einem bestimmten Nutzer, nur der Besitzer kann auf sein eigenes Konto zugreifen
- Beim Zugriff auf ein fremdes Konto antwortet die API mit 403 Forbidden
- Passwörter werden nie in API-Antworten zurückgegeben

## Testen

Die Endpunkte können z. B. mit Postman getestet werden. Zuerst über `POST /auth/registrieren` einen Nutzer anlegen (Body: `{"username": "...", "password": "..."}`). Danach bei allen weiteren Anfragen unter **Authorization → Basic Auth** die Zugangsdaten eintragen. Ohne Login antwortet die API mit 401.