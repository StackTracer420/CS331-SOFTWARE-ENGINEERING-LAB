UML Use Case Diagram  
Intelligent Business Process Automation (BPA) Platform



Overview

This document presents the **Use Case Diagram** for the *Intelligent Business Process Automation (BPA) Platform*.  
The diagram visually represents the interaction between different external actors and the system, highlighting the major functional requirements defined in the **Software Requirements Specification (SRS)**.

The goal of this use case model is to:
- Define the system scope
- Identify system users (actors)
- Describe how users interact with system functionalities
- Demonstrate intelligent automation features such as rule-based workflows, SLA-based escalation, and notifications



Actors

- Administrator  
  Responsible for workflow creation, rule configuration, analytics monitoring, and user management.

- Employee  
  Initiates workflows and completes assigned tasks.

- Manager  
  Reviews tasks, approves or rejects them, and monitors workflow progress.

- Email Notification Service
  External service used to send notifications related to task status, approvals, and escalations.



UML Use Case Diagram

> The following PlantUML code can be rendered using tools such as  
> PlantUML, draw.io, StarUML, or GitHub PlantUML plugins.

```plantuml
@startuml
left to right direction
skinparam packageStyle rectangle
skinparam shadowing false

actor Administrator
actor Employee
actor Manager
actor "Email Notification Service" as EmailService

rectangle "Intelligent Business Process Automation (BPA) Platform" {

  usecase "User Login" as UC_Login
  usecase "Create Workflow" as UC_CreateWF
  usecase "Define Business Rules" as UC_DefineRules
  usecase "Initiate Workflow" as UC_InitiateWF
  usecase "View Assigned Tasks" as UC_ViewTasks
  usecase "Complete Task" as UC_CompleteTask
  usecase "Approve / Reject Task" as UC_ApproveTask
  usecase "Escalate Delayed Task" as UC_Escalate
  usecase "Receive Notifications" as UC_Notify
  usecase "View Workflow Status" as UC_ViewStatus
  usecase "View Analytics Dashboard" as UC_Analytics
  usecase "Manage Users & Roles" as UC_ManageUsers
}

Administrator --> UC_Login
Administrator --> UC_CreateWF
Administrator --> UC_Analytics
Administrator --> UC_ManageUsers

Employee --> UC_Login
Employee --> UC_InitiateWF
Employee --> UC_ViewTasks
Employee --> UC_CompleteTask

Manager --> UC_Login
Manager --> UC_ViewTasks
Manager --> UC_ApproveTask
Manager --> UC_ViewStatus

EmailService --> UC_Notify

UC_CreateWF ..> UC_DefineRules : <<include>>
UC_InitiateWF ..> UC_Login : <<include>>
UC_CompleteTask ..> UC_Notify : <<include>>
UC_ApproveTask ..> UC_Notify : <<include>>
UC_Escalate ..> UC_Notify : <<include>>
UC_Analytics ..> UC_ViewStatus : <<include>>

UC_Escalate ..> UC_ApproveTask : <<extend>>\n[SLA Violation]

@enduml
