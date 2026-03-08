# CS 331 – Software Engineering Lab  
## Assignment 5 Report
### Project: Intelligent End-to-End Business Process Automation Platform

---

## I. Hosting Plan for Application Components 

### 1. Host Site (Deployment Environment)

The application components will be deployed using cloud-based hosting.

| Component | Hosting Location |
|------------|------------------|
| Presentation Layer (Frontend UI) | Web Server / Cloud CDN (AWS S3 + CloudFront / Netlify) |
| Workflow Engine | Application Server (AWS EC2 / Azure VM) |
| Rule Engine | Application Server |
| Task Manager | Application Server |
| Notification Service | Microservice Container (Docker) |
| Analytics Service | Backend Server |
| Failure Analyzer & Self-Healing Module | Backend Processing Server |
| Database Layer | Managed Database (AWS RDS / MongoDB Atlas) |

---

### 2. Deployment Strategy

**Step 1 — Server Configuration**
- Configure Linux cloud VM.
- Install Node.js / Java Runtime.
- Install Web Server (Nginx).
- Configure Docker containers.
- Install database client.

**Step 2 — Backend Deployment**
- Deploy business services:
  - Workflow Engine
  - Task Manager
  - Rule Engine
- Services exposed through REST APIs.

**Step 3 — Database Setup**
- Configure managed database.
- Create schemas:
  - Users
  - Tasks
  - Workflows
  - Logs
  - Analytics

**Step 4 — API Communication Setup**
- API Gateway routes requests:
  - Frontend → Backend APIs
  - Service → Service communication
- JSON-based REST communication enabled.

**Step 5 — Frontend Deployment**
- UI hosted separately.
- Connected using HTTPS API endpoints.

---

### 3. Security Measures (Optional)

- HTTPS using SSL/TLS encryption
- JWT-based authentication
- Role-Based Access Control (RBAC)
- Firewall rules restricting database access
- API rate limiting
- Encrypted credentials using environment variables

---

## II. End-User Access & System Interaction 

### A. How End Users Access Services 

Users interact with the system through a web browser interface.

**Access Flow**
- User opens BPA web application.
- Login authentication occurs.
- Requests sent to backend APIs.
- Workflow services process requests.
- Database stores/retrieves data.
- Results returned to dashboard.

---

### B. Pictorial Representation of Interaction

#### 1. User → Frontend → Backend Interaction

```
User
  ↓
Web Browser (Frontend UI)
  ↓
API Gateway
  ↓
Application Services
  ↓
Database Server
  ↓
Response to Dashboard
```

**Interaction Explanation**
- User interacts with Presentation Layer.
- Frontend sends API requests.
- Backend services execute business logic.
- Database provides persistent storage.
- Response returned to user dashboard.

---

#### 2. Internal Component Interaction (Backend)

```
Workflow Engine
     ↓
Rule Engine
     ↓
Task Manager
     ↓
Failure Analyzer
     ↓
Self-Healing Module
     ↓
Notification Service
     ↓
Analytics Service
     ↓
Database
```

**Component Communication Flow**
1. Workflow Engine initiates workflow.
2. Rule Engine evaluates conditions.
3. Task Manager assigns tasks.
4. Failure Analyzer monitors execution.
5. Self-Healing Module retries failures.
6. Notification Service alerts users.
7. Analytics Service records performance.

---

## III. Implementation of Application Components & Interaction 

### Implemented Component 1: User Management Service

**Responsibilities**
- User login
- Authentication
- Role verification

**Example APIs**
```
POST /api/login
GET /api/user/profile
```

---

### Implemented Component 2: Task Manager Service

**Responsibilities**
- Task creation
- Task assignment
- Status tracking

**Example APIs**
```
POST /api/tasks/create
GET /api/tasks/{id}
PUT /api/tasks/update
```

---

### Interaction Between Components

**Execution Flow**
1. User logs into system.
2. User starts workflow.
3. Workflow Engine requests Task Manager: createTask(workflowID)
4. Task Manager stores task in database.
5. Notification Service informs assigned employee.
6. Task completion updates workflow state.

---

### Component Interaction Sequence

```
User
 ↓
Frontend UI
 ↓
User Management Service
 ↓
Workflow Engine
 ↓
Task Manager
 ↓
Database
 ↓
Notification Service
 ↓
User Dashboard
```

---

## Conclusion

The BPA Platform deployment ensures:
- Cloud-based scalable hosting
- Secure API communication
- Clear user-to-system interaction
- Modular backend collaboration
- Practical implementation of core components

