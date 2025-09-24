# 🛒 Order Orchestrator (Spring Boot)

This is a **simplified Order Orchestration Service** built using **Spring Boot**.  
It simulates an order moving through different states in a workflow system.

---

## 🚀 Features
- REST APIs for creating and managing orders
- Order lifecycle:  
  `CREATED → VALIDATED → PACKED → SHIPPED → DELIVERED`
- Validation rules:
  - Cannot VALIDATE if items list is empty
  - Cannot SHIP if customer name is empty
- In-memory storage (HashMap)
- Logging of every state transition with timestamp

---

## ⚙️ Technologies
- Java 17
- Spring Boot 3
- Maven
- SLF4J / Logback for logging

---

## 📦 How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/<your-username>/order-orchestrator.git
   cd order-orchestrator
2.Build and run with Maven:

mvn spring-boot:run


3.Application will start at:

http://localhost:8080


