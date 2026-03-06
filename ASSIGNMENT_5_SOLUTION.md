# CS 331 – Software Engineering Lab
## Assignment 5: Part I – Hosting of Application Components

---

## 1. Host Site Architecture
The application components are hosted using a modern, distributed cloud-based deployment architecture. This ensures that the UI, business logic, and data storage are decoupled for better performance and maintenance.

### Infrastructure Overview

| Component | Platform | Purpose |
| :--- | :--- | :--- |
| **Frontend (UI)** | **Vercel** | Serves static frontend pages and provides fast global CDN delivery. |
| **Backend API** | **Render** | Handles application logic, processes user requests, and manages DB communication. |
| **Database** | **MongoDB Atlas** | Stores user data and application records with persistent cloud storage. |



---

## 2. Deployment Strategy
The deployment of the application components follows a systematic 6-step pipeline:

### Step 1: Development Environment
* **Frontend:** Developed using React / HTML / CSS / JavaScript.
* **Backend:** Developed using Node.js and the Express framework.

### Step 2: Server Configuration
* Install Node.js runtime environment.
* Configure environment variables (`.env`) for secrets.
* Setup RESTful API endpoints:
    * `POST /login`
    * `POST /register`
    * `GET /fetch-data`

### Step 3: Frontend Deployment
* Generate a production-ready build.
* Deploy build files to **Vercel**.
* Configure environment variables to point to the live Backend API URL.

### Step 4: Backend Deployment
* Connect the backend repository to **Render**.
* Configure the server port and database connection strings.
* Map API routes for client access.

### Step 5: Database Setup
* Create a cloud database cluster in **MongoDB Atlas**.
* Configure collections, search indexes, and user access permissions.

### Step 6: API Communication Flow
The system operates on a request-response cycle:
> **Frontend** → **Backend API** → **Database**
1.  Frontend sends HTTP requests.
2.  Backend processes logic and performs CRUD operations on the database.
3.  Formatted JSON response is returned to the frontend.

---

## 3. Security Measures
To protect data and prevent unauthorized access, the following measures are implemented:

1.  **HTTPS Encryption:** All data in transit is secured using SSL/TLS encryption.
2.  **API Authentication:** Implementation of **JWT (JSON Web Tokens)** to verify user identity.
3.  **Database Access Control:** * **IP Whitelisting:** Restricting DB access to the Render server's IP range.
    * **Secure Connection Strings:** Encrypted credentials for DB authentication.
4.  **Firewall Protection:** Leveraging built-in cloud firewalls provided by Vercel and Render to mitigate DDoS and malicious attacks.




## Assignment 5: Part II – User Access and System Interaction

---

## 1. User Interaction with the System
End users access the application through a standard client interface. The system is designed to be platform-independent, requiring only an active internet connection and a modern web engine.

### Access Methods:
* **Web Browsers:** Chrome, Firefox, Safari, Edge.
* **Mobile Devices:** Responsive web access via iOS and Android.
* **Connectivity:** Public or private internet connection to reach cloud-hosted endpoints.

Users interact directly with the **Frontend Interface**, which serves as the presentation layer and handles the translation of user actions into structured backend service requests.

---

## 2. System Interaction Diagram
The following diagram illustrates the request-response lifecycle between the client and the cloud infrastructure.



### Architecture Visual:

mermaid
graph TD
    User[End User / Web Browser] ---|HTTPS Request| Frontend[Frontend: Vercel]
    Frontend ---|API Call / JSON| Backend[Backend: Render]
    Backend ---|Query / Index| DB[(Database: MongoDB Atlas)]
    DB ---|Data Result| Backend
    Backend ---|HTTP Response| Frontend
    Frontend ---|UI Update| User
3. Interaction Steps (Operational Flow)
The process of a single user action (such as a login or data submission) follows these discrete steps:

Initialization: User opens the web application URL in a browser.

Asset Loading: The browser loads the UI interface from the Vercel cloud server.

User Trigger: User performs an action, such as clicking "Login" or submitting a form.

API Request: The Frontend sends an asynchronous HTTP request (Fetch/Axios) to the Render backend API.

Logic Processing: The Backend processes the incoming request and validates the payload.

Data Persistence: The Backend retrieves from or stores data in the MongoDB Atlas cluster.

Server Response: A JSON-formatted response is returned from the Backend to the Frontend.

UI Rendering: The Frontend receives the data and updates the display dynamically to reflect the result to the user.
