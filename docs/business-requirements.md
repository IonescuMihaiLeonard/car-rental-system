# 📄 Business Requirements Document  
## Proiect: Sistem de Închirieri Auto (Car Rental System)

---

## 🧩 1. Descriere generală

Sistemul de închirieri auto este o aplicație web menită să simplifice procesul de gestionare a rezervărilor și a flotei de mașini.  
Aplicația va permite clienților să caute mașini disponibile, să efectueze rezervări online, iar administratorilor să gestioneze flota, utilizatorii și rapoartele.  

Scopul proiectului este implementarea unui **MVP (Minimum Viable Product)** care să demonstreze funcționalitățile de bază pentru un sistem real de închirieri auto.

---

## 🚗 2. Cerințe de business

1. Clienții pot vizualiza lista mașinilor disponibile pentru închiriere.  
2. Clienții pot căuta mașini după marcă, model, preț sau categorie.  
3. Clienții pot face o rezervare pentru o perioadă de timp specificată.  
4. Sistemul verifică disponibilitatea mașinii și previne suprapunerile de rezervări.  
5. Clienții pot anula o rezervare înainte de data de început.  
6. Administratorii pot adăuga, modifica și șterge mașini din baza de date.  
7. Sistemul calculează automat costul total al rezervării în funcție de numărul de zile și tariful mașinii.  
8. Clienții pot lăsa o recenzie și un rating pentru mașinile închiriate.  

---

## 🎯 3. Funcționalități principale (MVP)

### 1️⃣ Gestionarea mașinilor
- CRUD complet pentru entitatea `Car`.  
- Administratorul poate adăuga, modifica sau șterge mașini.  
- Clienții pot vizualiza doar mașinile disponibile.  

### 2️⃣ Gestionarea rezervărilor
- Clienții pot crea, vizualiza și anula rezervări.  
- Sistemul verifică disponibilitatea și previne rezervările suprapuse.  
- Calcul automat al costului rezervării.

### 3️⃣ Calculul prețului și validarea datelor
- Tariful zilnic este definit pentru fiecare mașină.  
- Costul total se calculează automat în funcție de durata rezervării.  
- Validări pentru datele de început și sfârșit ale rezervării.

### 4️⃣ Recenzii și evaluări
- Clienții pot adăuga o recenzie și un rating după finalizarea rezervării.  
- Administratorii pot modera recenziile.  
- Recenziile sunt afișate public pentru fiecare mașină.

---

## 🧱 4. Entități principale

| Entitate | Descriere | Exemple de câmpuri |
|-----------|------------|--------------------|
| **Car** | Informații despre mașină | id, marca, model, categorie, prețZilnic, status |
| **User** | Utilizatorul sistemului | id, nume, email, parolă, rol |
| **Booking** | Rezervarea unei mașini | id, dataStart, dataEnd, totalCost, status |
| **Review** | Recenzie pentru o mașină | id, rating, comentariu, dataCreării |
| **Role** | Rol al utilizatorului | id, numeRol |

---

## 🧰 5. Tehnologii

- **Backend:** Java 17, Spring Boot, Spring Data JPA, Spring Validation, Spring Security  
- **Bază de date:** MySQL  
- **Documentare API:** Swagger 
- **Testare:** JUnit 5, Mockito  
- **Build tool:** Maven  
- **Testare API:** Postman  

---

## 🧪 6. Criterii de acceptare pentru MVP

✅ Toate endpoint-urile REST funcționează fără erori.  
✅ Toate testele unitare trec cu succes.  
✅ Codul respectă principiile **DRY** și convențiile Java.  
✅ Datele se persistă corect în baza de date.  
✅ Aplicația poate fi testată cu Postman sau prin interfața Swagger.

---

## 🧾 7. Resurse adiționale

- **README.md** – instrucțiuni de rulare și detalii despre proiect  
- **Postman Collection** – fișier pentru testarea endpoint-urilor  

---

## 👤 Autor

Proiect realizat de **Ionescu Mihai-Leonard**

---

