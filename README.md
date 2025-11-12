# 🐾 Pet Management REST API

A REST application built with **Spring Boot (Java 21)** for managing pets.  
The project was implemented as a coding assignment, following the principles of **Domain-Driven Design (DDD)** and **Hexagonal Architecture**.

---

## 🧱 Architecture Overview

The project follows a **Hexagonal (Ports & Adapters)** structure to ensure clean separation between the domain logic and infrastructure layers:

- **Domain** — core business entities and rules  
- **Application** — use cases and orchestration logic  
- **Adapters (in/out)** — REST controllers and persistence interfaces  

This approach makes the application easily adaptable to changes in the persistence mechanism — for example, switching from a relational to a non-relational database — without altering the core domain logic.

---

## 🚀 How to Run the Application

### Prerequisites
- **Java 21**
- **Maven**
- **Visual Studio Code** (with Java Extension Pack)

### Run
From the project root, execute:

```bash
mvn spring-boot:run
```

The application will start on:  
👉 `http://localhost:8080/api/pets`

---

## 🧪 Testing the API Endpoints

You can test the API endpoints using the **`pet_api_routes.http`** file included in the project.

### Steps
1. Open the project in **Visual Studio Code**.  
2. Install the **REST Client** extension (if not already installed).  
3. Open `pet_api_routes.http` and execute the requests directly.  

This file allows you to:
- Create a new pet  
- Retrieve all pets  
- Get a pet by ID  
- Update or delete a pet
- Check negatives scenarios

---

## 📂 Project Structure

```
src/
 ├─ adapter/
 │   ├─ in/        → REST controllers
 │   └─ out/       → Mock persistence adapters
 ├─ application/   → Application services
 └─ domain/        → Domain model and core logic
```

---

## 🧩 Technologies Used

- **Java 21**
- **Spring Boot**
- **Spring Web**
- **Maven**
---

## 🧭 Notes

The project emphasizes **clean architecture and maintainability**, with clear separation between the domain model and infrastructure details.  
Persistence can be replaced transparently by providing a new *outbound adapter*, with no changes required in the domain or application layers.
