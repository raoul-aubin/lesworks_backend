# LesWorks Backend

LesWorks ist eine REST-API für eine Jobplattform, auf der Arbeitgeber Stellenangebote veröffentlichen können und Arbeitssuchende sich auf diese Angebote bewerben können.

Dieses Projekt wurde mit **Spring Boot** entwickelt und verwendet **JWT (JSON Web Token)** für die Authentifizierung und Autorisierung.

---

## 🚀 Funktionen

- Benutzerregistrierung und Login
- JWT-basierte Authentifizierung
- Rollenbasierte Benutzer (CLIENT / JOBIST)
- CRUD-Funktionalität für Jobangebote
- Sicherheitsmechanismen (401 / 403)
- RESTful API Design

---

## 🛠️ Technologien

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JWT
- Maven

---

## 🔐 Sicherheit

- Passwort-Hashing mit BCrypt
- Stateless Authentication mit JWT
- Zugriffsschutz auf geschützte Endpunkte
- Nur der Ersteller eines Jobangebots darf es bearbeiten oder löschen

---

## 📡 API Endpunkte (Auswahl)

### Authentifizierung
- `POST /api/auth/register`
- `POST /api/auth/login`

### Jobangebote
- `POST /api/job-offers`
- `GET /api/job-offers`
- `GET /api/job-offers/{id}`
- `PUT /api/job-offers/{id}`
- `DELETE /api/job-offers/{id}`

---

## 🧪 Testing

Die API kann mit **Postman** getestet werden.  
Geschützte Endpunkte benötigen einen gültigen JWT im Header:


---

## 📌 Projektstatus

🟢 In Entwicklung  
Weitere Funktionen wie Bewerbungen (JobApplication) und erweiterte Rollenlogik sind geplant.

---

## 👤 Autor

Entwickelt von **[Raoul Tchangou]**  
