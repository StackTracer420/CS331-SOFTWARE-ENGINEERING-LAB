class Task {

    private String taskId;
    private String status;
    private int daysPending;

    public Task(String taskId, String status, int daysPending) {

        this.taskId = taskId;
        this.status = status;
        this.daysPending = daysPending;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getStatus() {
        return status;
    }

    public int getDaysPending() {
        return daysPending;
    }

    public void completeTask() {

        status = "Completed";
        System.out.println("Task " + taskId + " marked as Completed");
    }

    public void displayTaskDetails() {

        System.out.println("\n----- TASK DETAILS -----");

        System.out.println("Task ID      : " + taskId);
        System.out.println("Status       : " + status);
        System.out.println("Days Pending : " + daysPending);

        System.out.println("------------------------");
    }
}

class NotificationService {

    public void sendNotification(String userId, String message) {

        System.out.println("\n[Notification]");
        System.out.println("To      : " + userId);
        System.out.println("Message : " + message);
    }

    public void escalateTask(String taskId, String managerId) {

        System.out.println("\n[ESCALATION ALERT]");
        System.out.println("Task " + taskId + " escalated to Manager: " + managerId);
    }
}

class EscalationHandler {

    private NotificationService notifier;

    public EscalationHandler() {
        notifier = new NotificationService();
    }

    public void checkAndEscalate(Task task, String managerId) {

        if (!task.getStatus().equals("Completed") && task.getDaysPending() > 3) {

            notifier.escalateTask(task.getTaskId(), managerId);

            notifier.sendNotification(
                    managerId,
                    "Task " + task.getTaskId() + " has been pending for "
                            + task.getDaysPending() + " days and requires attention."
            );

        } else {

            System.out.println("\nNo escalation required for task " + task.getTaskId());
        }
    }
}

public class module2 {

    public static void main(String[] args) {

        System.out.println("===== ESCALATION MODULE =====");

        Task task1 = new Task("TASK101", "Pending", 5);
        Task task2 = new Task("TASK102", "In Progress", 2);

        EscalationHandler handler = new EscalationHandler();
        NotificationService notifier = new NotificationService();

        task1.displayTaskDetails();
        handler.checkAndEscalate(task1, "Manager01");

        task2.displayTaskDetails();
        handler.checkAndEscalate(task2, "Manager01");

        task2.completeTask();

        notifier.sendNotification(
                "Employee01",
                "Your assigned task TASK102 has been successfully completed."
        );

        System.out.println("\n===== End of Module 2 =====");
    }
}