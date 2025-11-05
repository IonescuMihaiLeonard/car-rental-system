# 🚗 Car Rental System

Un sistem de **închirieri auto** dezvoltat în **Spring Boot**, care permite gestionarea mașinilor, rezervărilor și utilizatorilor.  
Proiectul face parte dintr-un exercițiu practic pentru implementarea unui MVP (Minimum Viable Product) cu REST API, servicii, validări și persistarea datelor în baza de date.

---

## 🧩 Descriere generală

Sistemul de închirieri auto este o aplicație web care gestionează întregul flux de închiriere a mașinilor.  
Clienții pot vizualiza mașinile disponibile, pot face rezervări și pot lăsa recenzii, în timp ce administratorii pot adăuga, modifica sau șterge mașini și pot vizualiza rapoarte de activitate.

---

## 🏗️ Funcționalități principale (MVP)

1. **Gestionarea mașinilor** – CRUD complet (create, read, update, delete) pentru entitățile de tip mașină.  
2. **Gestionarea rezervărilor** – creare, anulare și vizualizare rezervări.  
3. **Autentificare și autorizare** – roluri pentru client și administrator.  
4. **Calcul automat al prețului** – pe baza perioadei și a tarifului zilnic al mașinii.  
5. **Recenzii clienți** – adăugare și afișare recenzii pentru fiecare mașină.

---

## 🧱 Arhitectură

Proiectul este construit folosind principiile **Spring Boot 3** și **arhitectura în straturi**:

- **Controller Layer** – gestionează cererile REST.
- **Service Layer** – implementează logica de business.
- **Repository Layer** – interacționează cu baza de date prin JPA/Hibernate.
- **Entity Layer** – modelează datele din sistem (Car, User, Booking, Review etc.).

---

## 🗃️ Entități principale

| Entitate  |                   Descriere                 |              Relații               |
|-----------|---------------------------------------------|------------------------------------|
| `User`    | Informații despre utilizator (client/admin) | One-to-Many cu `Booking`, `Review` |
| `Car`     | Mașină disponibilă pentru închiriere        | One-to-Many cu `Booking`, `Review` |
| `Booking` | Rezervare efectuată de un utilizator        | Many-to-One cu `User`, `Car`       |
| `Review`  | Recenzie scrisă de un client                | Many-to-One cu `User`, `Car`       |
| `Role`    | Tip de utilizator (CLIENT/ADMIN)            | One-to-Many cu `User`              |


---

## 🧪 Tehnologii utilizate

- **Java 17+**
- **Spring Boot 3**
- **Spring Data JPA / Hibernate**
- **Spring Validation**
- **Spring Security (JWT sau Basic Auth)**
- **H2 / MySQL Database**
- **Swagger / OpenAPI** – pentru documentarea endpoint-urilor
- **JUnit 5 & Mockito** – pentru testare
- **Maven** – pentru build și managementul dependențelor

---

## 🚀 Cum se rulează proiectul

1. Clonează repository-ul:
   ```bash
   git clone https://github.com/IonescuMihaiLeonard/car-rental-system.git
   cd car-rental-system
   ```
