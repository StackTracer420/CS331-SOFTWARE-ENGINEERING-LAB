# CS 331 – Software Engineering Lab  
## Assignment 4  
### Software Architecture Design – Intelligent BPA Platform  

---

# I. Selected Software Architecture Style

## Chosen Architecture: **Layered Architecture (Modular Monolithic with Service Abstractions)**

The Intelligent Business Process Automation (BPA) Platform follows a **Layered Architecture** approach with clearly separated concerns and modular service components.

---

## A. Justification Based on Component Granularity (5 Marks)

The system is divided into well-defined logical layers:

### 1️⃣ Presentation Layer
- Web Interface (Dashboard, Workflow UI)
- Analytics Dashboard
- Task Management Interface
- Notification UI

Granularity:
- UI modules are independent but communicate only with the Application Layer.
- No direct database access.

---

### 2️⃣ Application / Service Layer
- Workflow Engine
- Rule Engine
- Task Manager
- Escalation Manager
- Failure Analyzer
- Notification Service
- Analytics Service

Granularity:
- Each service performs a **single focused responsibility**.
- Services interact internally but remain logically independent.
- Business logic is centralized here.

---

### 3️⃣ Data Access Layer
- Workflow Repository
- User Repository
- Task Repository
- Logs Repository
- Analytics Data Store

Granularity:
- Each repository manages one type of entity.
- No business logic inside this layer.

---

### 4️⃣ Database Layer
- Relational database (e.g., MySQL / PostgreSQL)
- Stores structured workflow, task, and log data.

---

### Why It Qualifies as Layered Architecture

✔ Clear separation of concerns  
✔ Strict interaction between adjacent layers  
✔ Business logic isolated from UI  
✔ Data access isolated from business logic  

This confirms it belongs to the **Layered Architecture category**.

---

## B. Why Layered Architecture is Best for This Project (5 Marks)

### 1️⃣ Scalability
- Services in Application Layer can be scaled independently.
- Multiple workflows can run concurrently.
- Future microservices migration possible.

---

### 2️⃣ Maintainability
- Modular design allows:
  - Easy debugging
  - Isolated updates
  - Independent testing
- Rule Engine can be modified without affecting UI or database.

---

### 3️⃣ Performance
- Direct database access avoided from UI.
- Caching can be added in Service Layer.
- Failure detection handled asynchronously.

---

### 4️⃣ Security
- Credentials handled in Service Layer.
- Database access restricted to Data Layer.
- Role-based access implemented in Application Layer.

---

### 5️⃣ Extensibility
- AI-based anomaly detection can be added inside Analytics Service.
- New workflow types can be plugged into Workflow Engine.

---

# II. Application Components of the Intelligent BPA Platform (10 Marks)

Below are the key components:

---

## 1️⃣ User Management Component
- Handles authentication
- Manages roles (Admin, Manager, Employee)
- Access control logic

---

## 2️⃣ Workflow Engine
- Executes workflows
- Maintains state transitions
- Coordinates tasks

---

## 3️⃣ Rule Engine
- Stores business rules
- Evaluates workflow conditions
- Supports dynamic rule updates

---

## 4️⃣ Task Manager
- Assigns tasks
- Tracks task completion
- Handles task deadlines

---

## 5️⃣ Escalation Manager
- Detects delayed tasks
- Escalates to managers
- Logs escalation events

---

## 6️⃣ Failure Analyzer (Novelty Component)
- Monitors system logs
- Classifies failures
- Suggests recovery actions

---

## 7️⃣ Self-Healing Recovery Module (Innovative Feature)
- Automatically retries failed tasks
- Applies predefined recovery strategies
- Reduces manual intervention

---

## 8️⃣ Notification Service
- Sends email alerts
- Sends system notifications
- Handles escalation notifications

---

## 9️⃣ Analytics Service
- Generates KPI metrics
- Tracks workflow success rate
- Identifies bottlenecks
- Detects anomalies

---

## 🔟 Data Management Layer
- Workflow Database
- User Database
- Logs Database
- Analytics Data Store

---

# Summary

The Intelligent BPA Platform adopts a **Layered Architecture** with modular service components to ensure:

- High maintainability
- Strong scalability
- Clean separation of concerns
- Secure data handling
- Innovative self-healing capabilities
- Advanced analytics and anomaly detection

This architecture supports both current academic requirements and future enterprise-level expansion.

---
