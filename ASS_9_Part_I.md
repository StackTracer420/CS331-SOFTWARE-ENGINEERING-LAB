# Test Plan: Intelligent Self-Healing BPA Platform
**Course:** CS 331 (Software Engineering Lab)
**Project Component:** Assignment 9 - Q1 (a and b)

## 1. Objective of Testing
The primary objectives of testing this platform are to:
* **Validate Business Logic:** Ensure the `WorkflowRegistry` strictly enforces legal state transitions (e.g., DRAFT to PENDING_REVIEW) and blocks unauthorized actions.
* **Verify AI & Mathematical Accuracy:** Confirm that the `AIDecisionEngine` accurately calculates risk scores based on pending days, complexity, and user performance without exceeding boundary limits (100% max).
* **Ensure System Autonomy & Stability:** Verify that the `SelfHealingEngine` correctly reassigns or escalates tasks while strictly adhering to circuit-breaker logic to prevent infinite automation loops.
* **Confirm Data Integrity:** Ensure that the Java Data Access Object (`TaskDAO`) successfully executes CRUD operations in the MySQL database without silent failures.

## 2. Scope
**Modules and Features IN Scope:**
* **Workflow Operations:** Task creation, state transitions, and UI dynamic button rendering.
* **Predictive AI Module:** Risk score calculation (`predictDelay`) and action recommendation logic.
* **Self-Healing Module:** Smart assignment algorithms and escalation handling.
* **Data Access Layer (DAL):** Database connectivity, data insertion, retrieval, and updates.
* **API Endpoints:** Validation of `/api/tasks`, `/api/create`, `/api/action`, and `/api/run-ai`.

**Modules and Features OUT of Scope:**
* Network latency and stress/load testing.
* Third-party authentication or Single Sign-On (SSO) integrations.
* Cross-browser UI compatibility testing.

## 3. Types of Testing to be Performed
* **Unit Testing (White Box):** Isolating specific Java classes and methods (e.g., testing the specific mathematical output of `predictDelay()` or testing boundary conditions in the BLL) using custom test scripts to verify internal structural paths.
* **Integration Testing:** Verifying the data handshake between the Business Logic Layer (BLL) and the Data Access Layer (DAL). For example, ensuring that when the `SelfHealingEngine` triggers an escalation, the `TaskManager` successfully writes that update to the MySQL database via the `TaskDAO`.
* **System Testing (Black Box):** End-to-end functional testing from the user's perspective. This involves clicking buttons on the UI, submitting forms, triggering the AI diagnostics, and validating that the visual output and Event Logs behave as expected.

## 4. Tools
* **Java CLI / Custom Test Runner:** Used to execute White Box logic scripts (e.g., `WhiteBoxTester.java`) to bypass the UI and test internal engines directly.
* **Web Browser (Chrome/Edge/Firefox):** Used to perform manual UI testing, verify DOM updates, and inspect network payloads.
* **MySQL Workbench / XAMPP phpMyAdmin:** Used to query the database directly and verify that backend SQL transactions are actually persisting data correctly.
* **Postman / Browser Developer Tools:** Used to test the HTTP API endpoints directly, ensuring the Java `HttpServer` responds with the correct JSON payloads and status codes.

## 5. Entry and Exit Criteria
**Entry Criteria (When can testing begin?):**
* The source code (Java backend, HTML/JS frontend) compiles without syntax errors.
* The MySQL database server is actively running.
* The `bpa_platform_db` database and `tasks` table have been successfully created and populated with initial mock data.
* The Java HTTP Server is actively listening on port 8080.

**Exit Criteria (When is testing considered complete?):**
* All documented White Box and Black Box test cases have been executed.
* 100% of critical logic tests (like the infinite loop circuit-breaker and AI risk caps) have passed successfully.
* Data integrity is verified (UI data perfectly matches the MySQL database state).
* Any critical defects identified during testing have been patched and successfully retested.

---

### Q1. b) Test Cases for the Predictive AI & Self-Healing Module

**Target Module:** Predictive AI & Self-Healing Module (comprising `AIDecisionEngine` and `SelfHealingEngine`)
**Objective:** To verify that the AI correctly calculates risk scores based on multiple variables, assigns the appropriate mitigation strategy, and executes self-healing actions without causing infinite system loops.

| Test Case ID | Test Scenario / Description | Input Data (Task/System State) | Expected Output | Actual Output | Status |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **TC-AI-01** | **Risk Calc (Baseline):** Verify risk score when no penalty conditions are met. | `daysPending=1`<br>`complexity=5`<br>`userPerf=0.9` | `riskScore` = 0.0 | `riskScore` = 0.0 | **Pass** |
| **TC-AI-02** | **Risk Calc (Single Penalty):** Verify risk score when only the "Days Pending" limit (>2) is breached. | `daysPending=3`<br>`complexity=5`<br>`userPerf=0.9` | `riskScore` = 0.4 (+0.4 penalty) | `riskScore` = 0.4 | **Pass** |
| **TC-AI-03** | **Risk Calc (Multi-Penalty):** Verify cumulative addition of multiple risk factors. | `daysPending=4`<br>`complexity=8`<br>`userPerf=0.9` | `riskScore` = 0.7 (+0.4 + 0.3) | `riskScore` = 0.7 | **Pass** |
| **TC-AI-04** | **Risk Calc (Upper Boundary):** Verify that maximum combined penalties do not exceed 100% limit (Overflow Protection). | `daysPending=5`<br>`complexity=9`<br>`userPerf=0.2` | `riskScore` = 1.0 (Capped by `Math.min`) | `riskScore` = 1.0 | **Pass** |
| **TC-AI-05** | **AI Recommendation (Monitor):** Verify the engine recommends "MONITOR" for low-to-medium risk scores. | `riskScore=0.45` | Returns `"MONITOR"` | Returns `"MONITOR"` | **Pass** |
| **TC-AI-06** | **AI Recommendation (Escalate):** Verify the engine recommends "ESCALATE" for critical risk scores. | `riskScore=0.85` | Returns `"ESCALATE"` | Returns `"ESCALATE"` | **Pass** |
| **TC-SH-01** | **Self-Healing (Reassign):** Verify the system automatically routes the task to the user with the highest historical performance. | Action: `"REASSIGN"`<br>Stats: `Alice=0.9, Bob=0.3`<br>Task Assignee: `Bob` | Task assignee updates to `"Alice (AI)"`. `isHealed` becomes `true`. | Assignee = `"Alice (AI)"`. `isHealed` = `true`. | **Pass** |
| **TC-SH-02** | **Circuit Breaker Check:** Verify the system prevents infinite loops by ignoring tasks that have already been healed. | Action: `"ESCALATE"`<br>Task `isHealed`: `true` | Engine immediately returns. Task state remains completely unchanged. | State remained unchanged. Action aborted. | **Pass** |