import java.util.HashMap;
import java.util.Map;

public class WhiteBoxTester {

    public static void main(String[] args) {
        System.out.println(" STARTING WHITE BOX TEST SUITE...\n");

        runWBT01();
        runWBT02();
        runWBT03();
        runWBT04();

        System.out.println("\n ALL WHITE BOX TESTS COMPLETED.");
    }

    // WBT-01: Test AIDecisionEngine Path Coverage
    private static void runWBT01() {
        System.out.print("[WBT-01] Path Coverage (Predict Delay): ");
        Main.AIDecisionEngine ai = new Main.AIDecisionEngine();
        
        // Input: 3 days pending (+0.4), complexity 8 (+0.3), performance 0.5 (+0.3)
        Task testTask = new Task("T1", "Test", "User", "DRAFT", 3, 8, 0.5);
        double result = ai.predictDelay(testTask);
        
        // Expected: 0.4 + 0.3 + 0.3 = 1.0
        if (result == 1.0) {
            System.out.println("PASS (Result exactly 1.0)");
        } else {
            System.out.println("FAIL (Got: " + result + ")");
        }
    }

    // WBT-02: Test AIDecisionEngine Boundary Logic
    private static void runWBT02() {
        System.out.print("[WBT-02] Boundary Logic (Predict Delay Cap): ");
        Main.AIDecisionEngine ai = new Main.AIDecisionEngine();
        
        // Input: Values that would theoretically result in 1.0+ score
        Task testTask = new Task("T2", "Test", "User", "DRAFT", 5, 10, 0.1);
        double result = ai.predictDelay(testTask);
        
        // Expected: Math.min() should cap the score strictly at 1.0
        if (result == 1.0) {
            System.out.println("PASS (Score capped safely at 1.0)");
        } else {
            System.out.println("FAIL (Got: " + result + ")");
        }
    }

    // WBT-03: Test SelfHealingEngine Circuit Breaker (Condition Coverage)
    private static void runWBT03() {
        System.out.print("[WBT-03] Condition Coverage (Infinite Loop Breaker): ");
        Main.NotificationEngine ne = new Main.NotificationEngine();
        Main.SmartAssignmentEngine sa = new Main.SmartAssignmentEngine();
        Main.EscalationHandler eh = new Main.EscalationHandler(ne);
        Main.SelfHealingEngine healingEngine = new Main.SelfHealingEngine(sa, eh);
        
        Task testTask = new Task("T3", "Test", "Alice", "DRAFT", 0, 5, 0.9);
        
        // 🚨 WE SET THE FLAG TO TRUE BEFORE RUNNING THE ENGINE
        testTask.isHealed = true; 
        
        Map<String, Double> mockStats = new HashMap<>();
        mockStats.put("Bob", 0.99);

        // Attempt to trigger a reassignment
        healingEngine.takeAction(testTask, "REASSIGN", mockStats);
        
        // Expected: The engine should RETURN immediately and NOT change Alice to Bob
        if (testTask.assignedTo.equals("Alice")) {
            System.out.println("PASS  (Engine aborted correctly, loop prevented)");
        } else {
            System.out.println("FAIL  (Assignee changed to: " + testTask.assignedTo + ")");
        }
    }

    // WBT-04: Test WorkflowRegistry Branch Logic
    private static void runWBT04() {
        System.out.print("[WBT-04] Branch Logic (Invalid State Transition): ");
        Main.WorkflowRegistry registry = new Main.WorkflowRegistry();
        
        // Setup Rules
        registry.addRule("DRAFT", "SUBMIT", "PENDING_REVIEW");
        
        // Action: Try to "APPROVE" a "DRAFT" (which is illegal)
        String nextState = registry.getNextState("DRAFT", "APPROVE");
        
        // Expected: The registry should reject the action and return the original state ("DRAFT")
        if (nextState.equals("DRAFT")) {
            System.out.println("PASS (Invalid action blocked, remained in DRAFT)");
        } else {
            System.out.println("FAIL (State illegally changed to: " + nextState + ")");
        }
    }
}