import java.util.ArrayList;
import java.util.List;

public class module1 {

    static class Task {

        private String taskId;
        private String description;
        private String assignedTo;
        private String status;

        public Task(String taskId, String description) {
            this.taskId = taskId;
            this.description = description;
            this.status = "Pending";
        }

        public void assignTask(String userId) {
            assignedTo = userId;
            status = "Assigned";
            System.out.println("Task [" + taskId + "] assigned to " + userId);
        }

        public void completeTask() {

            if (assignedTo == null) {
                System.out.println("Task [" + taskId + "] cannot be completed (Not Assigned)");
                return;
            }

            status = "Completed";
            System.out.println("Task [" + taskId + "] completed successfully");
        }

        public String getStatus() {
            return status;
        }

        public String getTaskId() {
            return taskId;
        }

        public String getDescription() {
            return description;
        }

        public void displayTask() {

            System.out.println("Task ID      : " + taskId);
            System.out.println("Description  : " + description);
            System.out.println("Assigned To  : " + (assignedTo == null ? "Not Assigned" : assignedTo));
            System.out.println("Status       : " + status);
            System.out.println("-------------------------------");
        }
    }

    static class Workflow {

        private String workflowId;
        private String workflowName;
        private String status;
        private List<Task> tasks;

        public Workflow(String workflowId, String workflowName) {

            this.workflowId = workflowId;
            this.workflowName = workflowName;
            this.status = "Created";
            tasks = new ArrayList<>();
        }

        public void addTask(Task task) {
            tasks.add(task);
            System.out.println("Task added to workflow: " + task.getTaskId());
        }

        public void startWorkflow() {

            status = "In Progress";
            System.out.println("\nWorkflow '" + workflowName + "' started");
        }

        public void completeWorkflow() {

            for (Task t : tasks) {

                if (!t.getStatus().equals("Completed")) {
                    System.out.println("\nWorkflow cannot be completed. Some tasks are pending.");
                    return;
                }
            }

            status = "Completed";
            System.out.println("\nWorkflow completed successfully!");
        }

        public void displayWorkflow() {

            System.out.println("\n===== WORKFLOW DETAILS =====");

            System.out.println("Workflow ID   : " + workflowId);
            System.out.println("Workflow Name : " + workflowName);
            System.out.println("Status        : " + status);

            System.out.println("\n--- TASK LIST ---");

            for (Task t : tasks) {
                t.displayTask();
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("===== WORKFLOW MANAGEMENT MODULE =====");

        Workflow wf = new Workflow("WF101", "Document Approval");

        Task t1 = new Task("T1", "Draft Document");
        Task t2 = new Task("T2", "Review Document");
        Task t3 = new Task("T3", "Final Approval");

        wf.addTask(t1);
        wf.addTask(t2);
        wf.addTask(t3);

        wf.startWorkflow();

        t1.assignTask("UserA");
        t2.assignTask("UserB");
        t3.assignTask("Manager");

        t1.completeTask();
        t2.completeTask();

        wf.completeWorkflow();

        t3.completeTask();

        wf.completeWorkflow(); 

        wf.displayWorkflow();

        System.out.println("===== End of Module 1 =====");
    }
}