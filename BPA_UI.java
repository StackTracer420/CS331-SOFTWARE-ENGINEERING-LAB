import java.util.Scanner;

public class BPA_UI {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        module1.Workflow workflow =
                new module1.Workflow("WF100", "Document Approval");

        module1.Task task1 =
                new module1.Task("T1", "Prepare Document");

        module1.Task task2 =
                new module1.Task("T2", "Manager Approval");

        workflow.addTask(task1);
        workflow.addTask(task2);

        EscalationHandler escalation = new EscalationHandler();
        AuditManager audit = new AuditManager();

        int choice;

        do {

            System.out.println("\n===== BPA SYSTEM MENU =====");
            System.out.println("1. Start Workflow");
            System.out.println("2. Assign Task");
            System.out.println("3. Complete Task");
            System.out.println("4. Check Escalation");
            System.out.println("5. View Workflow Details");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    workflow.startWorkflow();
                    audit.recordEvent("Workflow started");
                    break;

                case 2:
                    System.out.println("Assigning tasks...");
                    task1.assignTask("Employee01");
                    task2.assignTask("Manager01");
                    audit.recordEvent("Tasks assigned");
                    break;

                case 3:
                    System.out.println("Completing tasks...");
                    task1.completeTask();
                    task2.completeTask();
                    workflow.completeWorkflow();
                    audit.recordEvent("Tasks completed");
                    break;

                case 4:
                    Task delayedTask =
                            new Task("T2", "Pending", 5);

                    escalation.checkAndEscalate(delayedTask, "Manager01");
                    audit.recordEvent("Escalation checked");
                    break;

                case 5:
                    workflow.displayWorkflow();
                    audit.displayLogs();
                    break;

                case 6:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);

        sc.close();
    }
}