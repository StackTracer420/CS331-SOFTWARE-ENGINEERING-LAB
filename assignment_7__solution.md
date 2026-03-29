# CS 331 – Software Engineering Lab

## Assignment 7 Report

**Project:** Intelligent Business Process Automation (BPA) Platform

---

# Q1. Core Functional Modules in Business Logic Layer (BLL)

The **Business Logic Layer (BLL)** is responsible for implementing core application logic and enforcing business rules. It acts as an intermediary between the presentation layer (UI) and the data layer.

## Core Modules

### 1. Workflow Engine

* Controls execution of workflows
* Maintains workflow state transitions
* Ensures correct sequence of operations

### 2. Task Manager

* Assigns tasks to users
* Tracks task progress and completion
* Updates workflow state

### 3. User Management Module

* Handles authentication and authorization
* Implements role-based access control (Admin, Manager, Employee)

### 4. Escalation Handler

* Detects delayed tasks
* Escalates tasks to higher authority

### 5. Notification Service

* Sends alerts and notifications
* Notifies users about task updates and escalations

### 6. Analytics Service

* Generates reports and insights
* Tracks system performance

## Interaction with Presentation Layer

1. User interacts with UI (dashboard)
2. UI sends API request to backend
3. BLL processes request using appropriate module
4. Data is fetched/updated in database
5. Response is returned to UI

---

# Q2(A). Business Rules Implementation

Business rules define how the system behaves under different conditions.

## Implemented Rules

### Authentication Rules

* Users must provide valid credentials
* JWT-based authentication is used

### Role-Based Access Rules

* Admin: Full access
* Manager: Review and approve tasks
* Employee: Limited to assigned tasks

### Workflow Rules

* Tasks must follow defined sequence
* No skipping of workflow steps

### Escalation Rules

* Tasks exceeding deadlines are escalated

### Task Assignment Rules

* Tasks assigned based on role and availability

---

# Q2(B). Validation Logic

Validation ensures that input data is correct and consistent before processing.

## Implemented Validation

### Login Validation

* Email format check
* Password constraints

### Task Validation

* Task fields cannot be empty
* Deadlines must be valid

### Workflow Validation

* Required steps must be defined
* Prevent invalid workflow configurations

### API Validation

* Required fields checked before processing

## Benefits

* Prevents invalid data entry
* Improves system reliability
* Enhances security

---

# Q2(C). Data Transformation

Data transformation ensures compatibility between backend data and frontend display.

## Implementation

### Backend Processing

* Raw database data is processed and formatted

### Transformation Examples

* Dates formatted to readable format
* Status values converted to UI-friendly labels
* IDs mapped to meaningful values

### API Response Structure

```json
{
  "success": true,
  "data": []
}
```

## Purpose

* Improves readability
* Ensures UI compatibility
* Separates database schema from UI

---

# Conclusion

The Business Logic Layer of the BPA platform efficiently manages workflows, enforces business rules, validates data, and transforms information for presentation. This ensures a scalable, secure, and maintainable system architecture.
