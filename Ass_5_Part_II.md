# CS 331 – Software Engineering Lab
## Assignment 5
## Part II – User Access and Component Interaction
### Project: Intelligent Business Process Automation (BPA) Platform

---

# II. How End Users Access These Services

The BPA platform provides services through a web-based interface that allows different types of users to interact with the workflow automation system.

---

# 1. Types of End Users

The system supports the following user roles:

• **Administrator**
  - Creates and manages workflows  
  - Monitors system analytics  
  - Manages users and permissions  

• **Manager**
  - Reviews tasks assigned to employees  
  - Approves or rejects workflow steps  
  - Receives escalation notifications  

• **Employee**
  - Views assigned tasks  
  - Completes workflow activities  
  - Updates task status  

---

# 2. User Access Method

End users access the services through a **web dashboard**.

Steps involved:

1. User opens the BPA web portal using a browser.
2. User logs in with secure credentials.
3. The system authenticates the user.
4. The dashboard displays user-specific tasks and workflows.
5. Users interact with workflows by completing or approving tasks.
6. Backend services process the requests and update system data.

---

# 3. Interaction Between User and Frontend

The frontend acts as the main interface between the user and the system.

Responsibilities of the frontend include:

• Displaying task lists  
• Providing workflow visualization  
• Showing analytics dashboard  
• Sending requests to backend services through APIs  

---

## User–System Interaction Diagram

```mermaid
flowchart LR

User["End User\n(Admin / Manager / Employee)"]

Frontend["Web Dashboard\n(Frontend UI)"]

Backend["Application Backend\nWorkflow Engine\nTask Manager\nEscalation Handler\nNotification Service\nAnalytics Service"]

Database["Database\nWorkflows\nTasks\nLogs\nAnalytics"]

User -->|Login / Task Actions| Frontend
Frontend -->|REST API Requests| Backend
Backend -->|Read / Write Data| Database
Database --> Backend
Backend -->|Response Data| Frontend
Frontend -->|Updated Dashboard| User

---

# 4. Interaction Between Application Components

The backend system consists of several application components that communicate with each other to execute workflows.

Major backend components include:

• Workflow Engine  
• Task Manager  
• Escalation Handler  
• Notification Service  
• Analytics Service  

These components collaborate to manage workflow execution.

---

# Component Interaction Flow


---

# 2. Component Interaction Flow (Mermaid)

```mermaid
flowchart TD

UserRequest["User Request"]

Workflow["Workflow Engine"]
TaskManager["Task Manager"]
Escalation["Escalation Handler"]
Notification["Notification Service"]
Database["Database"]

UserRequest --> Workflow
Workflow --> TaskManager
TaskManager --> Escalation
Escalation --> Notification
Notification --> Database
TaskManager --> Database
Workflow --> Database

---

# 5. Backend Component Responsibilities

## Workflow Engine
• Controls workflow execution  
• Initiates tasks  
• Tracks workflow progress  

---

## Task Manager
• Assigns tasks to users  
• Tracks task completion status  
• Updates workflow state  

---

## Escalation Handler
• Detects delayed or incomplete tasks  
• Escalates tasks to managers  

---

## Notification Service
• Sends alerts and notifications  
• Notifies managers of escalations  
• Confirms task completion  

---

## Analytics Service
• Generates workflow performance reports  
• Displays analytics in dashboard  

---

# 6. Data Flow in the System

The data flow between components follows these steps:

1. User performs an action from the dashboard.
2. The request is sent to the backend API.
3. The Workflow Engine processes the request.
4. Task Manager updates task status.
5. Escalation Handler checks deadlines.
6. Notification Service sends alerts if required.
7. Database stores all workflow information.

---
## Backend System Architecture Diagram

```mermaid
flowchart TD

Frontend["Frontend\n(Web Dashboard UI)"]

Workflow["Workflow Engine"]
TaskManager["Task Manager"]
Escalation["Escalation Handler"]
Notification["Notification Service"]
Analytics["Analytics Service"]

Database["Database\nWorkflows\nTasks\nLogs\nAnalytics"]

Frontend -->|API Request| Workflow
Workflow --> TaskManager
TaskManager --> Escalation
Escalation --> Notification

Workflow --> Analytics

Workflow --> Database
TaskManager --> Database
Analytics --> Database

Notification --> Frontend

---

# Summary

The BPA platform allows users to access workflow automation services through a web-based interface. The frontend communicates with backend application components through APIs.

Key features of the interaction architecture include:

• Web-based user interface  
• API-based communication between components  
• Modular backend architecture  
• Automated task escalation and notifications  
• Centralized workflow database  

This design ensures efficient workflow management and seamless interaction between users and system components.