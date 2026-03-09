public class BPASystemSimulation {

    public static void main(String[] args) {

        System.out.println("===== BUSINESS PROCESS AUTOMATION SYSTEM =====");
        RoleManager roleManager = new RoleManager();

        User emp = new User("U101", "Alice", "Employee");
        User manager = new User("U102", "Bob", "Manager");

        roleManager.addUser(emp);
        roleManager.addUser(manager);

        roleManager.showAllUsers();
        module1.Workflow workflow =
                new module1.Workflow("WF201", "Purchase Approval Workflow");

        module1.Task task1 =
                new module1.Task("T1", "Submit Purchase Request");

        module1.Task task2 =
                new module1.Task("T2", "Manager Approval");

        workflow.addTask(task1);
        workflow.addTask(task2);

        workflow.startWorkflow();
        TaskAssignmentService assignService = new TaskAssignmentService();

        assignService.assignTaskToUser("T1", emp);
        assignService.assignTaskToUser("T2", manager);

        task1.assignTask(emp.getUserId());
        task2.assignTask(manager.getUserId());
        AuditManager audit = new AuditManager();

        audit.recordEvent("Workflow WF201 started");
        audit.recordEvent("Task T1 assigned to " + emp.getUserId());

        EscalationHandler escalationHandler = new EscalationHandler();

        Task delayedTask =
                new Task("T2", "Pending", 5); // Simulate delay

        escalationHandler.checkAndEscalate(delayedTask, manager.getUserId());

        task1.completeTask();
        task2.completeTask();

        audit.recordEvent("Task T1 completed");
        audit.recordEvent("Task T2 completed");


        workflow.completeWorkflow();

        workflow.displayWorkflow();

        
        ReportGenerator report = new ReportGenerator();

        report.generateWorkflowReport(2, 2);

        audit.displayLogs();

        System.out.println("\n===== BPA SYSTEM SIMULATION COMPLETED =====");
    }
}