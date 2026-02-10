# Data Flow Diagram – Level 0 (Context Diagram)
## Intelligent Business Process Automation (BPA) Platform

---

## Purpose of the Diagram

The Level 0 Data Flow Diagram (also known as the Context Diagram) provides a high-level view of the Intelligent Business Process Automation (BPA) Platform. It represents the system as a single unified process and illustrates how external entities interact with the system through data exchange.

This diagram helps define the **system boundary**, identify **external actors**, and understand the **overall data flow** without revealing internal processing details.

---

## External Entities

The following external entities interact with the system:

- **Administrator**  
  Configures workflows, defines business rules, manages users, and views analytics reports.

- **Employee**  
  Initiates workflows, performs assigned tasks, and receives notifications.

- **Manager**  
  Approves or rejects tasks and monitors workflow progress.

- **Email Notification Service**  
  An external service used to deliver automated email notifications.

---

## Major Data Flows

- Administrators provide workflow configurations, business rules, and user data to the system and receive analytics insights and system performance reports.
- Employees submit workflow initiation requests and task execution updates, and receive assigned tasks and notifications.
- Managers send approval decisions and monitoring requests and receive task details and workflow status information.
- The system communicates with the Email Notification Service to send notification payloads and receive delivery acknowledgements.

---

## Significance

This context diagram establishes the scope of the Intelligent BPA Platform and clarifies how different stakeholders interact with the system at a conceptual level. It ensures a shared understanding among developers, analysts, and evaluators regarding system boundaries and responsibilities.

---
