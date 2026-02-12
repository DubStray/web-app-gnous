# Task Management Web App

Web Application per la gestione di task e wallet, realizzata come prova tecnica
Il progetto è diviso in due moduli principali:

- **Backend**: Spring Boot (Java 21) + PostgreSQL
- **Frontend**: Angular 21 + PrimeNG e Bootstrap

---

## Tecnologie Utilizzate

- **Backend Framework**: Spring Boot 3.4.x (Java 21)
- **Database**: PostgreSQL 18
- **Frontend Framework**: Angular 21
- **UI Components**: PrimeNG 18+
- **Containerization**: Docker & Docker Compose

---

## Prerequisiti

Assicurarsi di avere installato::

- **Docker & Docker Compose** (Per il database e backend)
- **Java 21** (Per il backend)
- **Node.js** e **npm** (Per il frontend)

---

## Backend Setup

Il backend si trova nella cartella `technicaltest-backend`.

### Esecuzione con Docker

Questo metodo avvia sia il database PostgreSQL che il servizio Backend in container, configurati automaticamente per comunicare tra loro

1. Spostati nella cartella del backend:

   ```bash
   cd technicaltest-backend
   ```

2. Avvia i container:

   ```bash
   docker compose up --build
   ```

> **Nota**:
>
> - Il Backend sarà accessibile a `http://localhost:8080`
> - Il Database PostgreSQL sarà accessibile esternamente a `localhost:5433` (user: `postgres`, password: `postgres`, db: `gnous_test_db`)

### Variabili d'Ambiente (Backend)

Il backend supporta le seguenti variabili d'ambiente per la configurazione (utili in Docker o deployment):

| Variabile                       | Descrizione                           | Default                                          |
| ------------------------------- | ------------------------------------- | ------------------------------------------------ |
| `SPRING_DATASOURCE_URL`         | URL JDBC di connessione al DB         | `jdbc:postgresql://localhost:5432/gnous_test_db` |
| `SPRING_DATASOURCE_USERNAME`    | Username del database                 | `postgres`                                       |
| `SPRING_DATASOURCE_PASSWORD`    | Password del database                 | `postgres`                                       |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | Strategia schema (update/create/none) | `update`                                         |

---

## Frontend Setup

Il frontend si trova nella cartella `technicaltest-frontend`.

1. Spostati nella cartella del frontend:

   ```bash
   cd technicaltest-frontend
   ```

2. Installa le dipendenze (solo la prima volta):

   ```bash
   npm install
   ```

3. Avvia il server di sviluppo:

   ```bash
   npm start
   ```

   Oppure se hai Angular CLI globale: `ng serve`.

4. Apri il browser all'indirizzo:
   [http://localhost:4200](http://localhost:4200)

---

## Endpoints Principali

- **Frontend**: [http://localhost:4200](http://localhost:4200)
- **Backend API**: [http://localhost:8080](http://localhost:8080)

## Troubleshooting

- **Porta occupata**: Se la porta 8080 o 5432/5433 è occupata, modifica i mapping nel `docker-compose.yaml` o chiudi i servizi in conflitto.
- **Errori Database**: Se il backend non parte, verifica che PostgreSQL sia attivo e le credenziali in `application.properties` siano corrette.
