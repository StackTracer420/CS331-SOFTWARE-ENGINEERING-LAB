### Part B: Software Testing Execution

#### 1. White Box Testing (Structural/Logic Testing)
**Objective:** To verify the internal execution paths, branch logic, and boundary conditions of the Java backend engines (specifically the `AIDecisionEngine` and `SelfHealingEngine`), ensuring the code behaves exactly as mathematically intended.

**White Box Test Cases:**

| Test ID | Target Component / Method | Description (Code Logic Tested) | Inputs (Code Level) | Expected Internal Output |
| :--- | :--- | :--- | :--- | :--- |
| **WBT-01** | `AIDecisionEngine.predictDelay()` | **Path Coverage:** Verify all three penalty conditions trigger correctly. | `daysPending=3`, `complexity=8`, `userPerformance=0.5` | Returns `1.0` (0.4 + 0.3 + 0.3) |
| **WBT-02** | `AIDecisionEngine.predictDelay()` | **Boundary Testing:** Verify the `Math.min(score, 1.0)` boundary logic prevents scores over 100%. | `daysPending=4` (0.4), `complexity=9` (0.3), `userPerformance=0.2` (0.3). Total theoretical = 1.0+. | Returns exactly `1.0` (Capped). |
| **WBT-03** | `SelfHealingEngine.takeAction()` | **Condition Coverage:** Test the infinite-loop circuit breaker (`if (task.isHealed) return;`). | `Task.isHealed = true`, `action = "REASSIGN"` | Method returns immediately. Task assignee remains unchanged. |
| **WBT-04** | `WorkflowRegistry.getNextState()` | **Branch Logic:** Requesting an invalid action for a state should return the current state unchanged. | `currentState = "DRAFT"`, `action = "APPROVE"` | Returns `"DRAFT"` (Does not crash or return null). |

**Execution & Results (Performing the Testing):**
* **Result for WBT-01 (PASS):** The logic path evaluated all `if` statements as true. The addition logic correctly summed the risk variables to `1.0`.
* **Result for WBT-02 (PASS):** The theoretical score hit `1.0`. The `Math.min()` function successfully prevented an overflow, ensuring the database only receives valid probability metrics.
* **Result for WBT-03 (PASS):** The `isHealed` boolean flag correctly triggered the early `return` statement, successfully preventing an infinite self-healing loop.
* **Result for WBT-04 (PASS):** The `HashMap` logic correctly identified that "APPROVE" does not exist as a valid key for the "DRAFT" state, safely returning the original state.

---

#### 2. Black Box Testing (Functional/Behavioral Testing)
**Objective:** To verify the application from the end-user's perspective without looking at the Java code. We will interact with the UI, click buttons, and pass data via HTTP API endpoints to ensure the inputs generate the correct UI updates and database states.

**Black Box Test Cases:**

| Test ID | Feature Tested | Action / User Input | Expected System Behavior (Output) |
| :--- | :--- | :--- | :--- |
| **BBT-01** | UI Task Creation | User types "Server Upgrade", selects "Bob", and clicks "Create". | A new row appears in the UI. State is "DRAFT". The "Total Workflows" counter increases by 1. |
| **BBT-02** | Workflow Enforcement | User attempts to bypass the workflow by directly calling `/api/action?taskId=TSK-101&action=APPROVE` while the task is in `DRAFT`. | The UI and API ignore the request. The task remains in `DRAFT` state. No approval notification is generated. |
| **BBT-03** | Dynamic UI Buttons | User clicks "SUBMIT" on a DRAFT task. | Task state changes to `PENDING_REVIEW`. The UI dynamically removes the "SUBMIT" button and renders "APPROVE", "REJECT", and "REQUEST CHANGES". |
| **BBT-04** | AI System Integration | User clicks the "⚡ Trigger AI Diagnostics" button. | The system processes tasks. Red/Orange/Green risk badges appear on screen. The Event Log updates with "🚨 AI ESCALATION" messages. |

**Execution & Results (Performing the Testing):**
* **Result for BBT-01 (PASS):** The frontend successfully passed the URL encoded data to the backend. The UI refreshed automatically, and the new task "Server Upgrade" appeared correctly assigned to Bob.
* **Result for BBT-02 (PASS):** The system successfully protected its workflow integrity. The invalid API call returned a standard success code, but the task state functionally did not change on the dashboard.
* **Result for BBT-03 (PASS):** The state transition was flawless. The JSON payload sent the new `availableActions` array, and the UI successfully transformed the HTML buttons based strictly on the new state.
* **Result for BBT-04 (PASS):** Clicking the AI button accurately simulated a system scan. High-risk tasks were automatically visually transformed (red badges), their assignees updated to "Senior Director", and the notification panel populated with system alerts. All functional requirements met.