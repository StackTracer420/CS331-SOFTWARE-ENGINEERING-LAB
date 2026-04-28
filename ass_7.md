# CS 331 – Software Engineering Lab  
## Assignment 7  
## Business Logic Layer (BLL) Implementation  
### Project: Intelligent Self-Healing Business Process Automation Platform  

---

# 1. Introduction

The Business Logic Layer (BLL) is responsible for implementing the core functionality of the BPA system. It acts as an intermediary between the presentation layer (web dashboard UI) and the data layer, ensuring that all workflow operations follow defined business rules.

In this project, the BLL is enhanced with intelligent capabilities such as prediction, automated decision-making, and self-healing mechanisms.

---

# 2. Core Functional Modules in BLL

The following modules form the Business Logic Layer:

• Workflow Engine  
• Task Manager  
• AI Decision Engine  
• Self-Healing Engine  
• Escalation Engine  
• Smart Assignment Engine  
• Analytics Engine  

These modules collectively manage workflow execution, decision-making, and optimization.

---

# 3. Module Descriptions

## 3.1 Workflow Engine
• Controls lifecycle of workflows  
• Initiates and completes workflows  
• Coordinates tasks  

## 3.2 Task Manager
• Handles task creation and updates  
• Tracks task status  
• Maintains assignment information  

## 3.3 AI Decision Engine
• Predicts delay probability  
• Suggests actions based on risk  

## 3.4 Self-Healing Engine
• Automatically resolves workflow issues  
• Reassigns or escalates tasks  

## 3.5 Escalation Engine
• Handles critical delays  
• Sends alerts to managers  

## 3.6 Smart Assignment Engine
• Assigns tasks based on performance  
• Optimizes resource allocation  

## 3.7 Analytics Engine
• Generates performance metrics  
• Provides workflow insights  

---

flowchart LR

UI[Presentation Layer (Dashboard)]

BLL[Business Logic Layer]

DB[Data Layer]

UI --> BLL
BLL --> DB


flowchart TD

UserAction[User Action]

Workflow[Workflow Engine]
TaskManager[Task Manager]

AI[AI Decision Engine]
SelfHeal[Self-Healing Engine]
Escalation[Escalation Engine]

Analytics[Analytics Engine]

UserAction --> Workflow
Workflow --> TaskManager
TaskManager --> AI

AI --> SelfHeal
AI --> Escalation

TaskManager --> Analytics
