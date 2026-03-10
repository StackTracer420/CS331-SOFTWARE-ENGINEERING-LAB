import java.util.ArrayList;
import java.util.List;

class AuditLog {

    private String event;
    private String timestamp;

    public AuditLog(String event, String timestamp) {
        this.event = event;
        this.timestamp = timestamp;
    }

    public void displayLog() {
        System.out.println("Event      : " + event);
        System.out.println("Timestamp  : " + timestamp);
        System.out.println("--------------------------------");
    }
}

class AuditManager {

    private List<AuditLog> logs;

    public AuditManager() {
        logs = new ArrayList<>();
    }

    public void recordEvent(String event) {

        String time = java.time.LocalDateTime.now().toString();

        AuditLog log = new AuditLog(event, time);
        logs.add(log);

        System.out.println("Audit Event Recorded: " + event);
    }

    public void displayLogs() {

        System.out.println("\n===== AUDIT LOG REPORT =====");

        for (AuditLog log : logs) {
            log.displayLog();
        }
    }
}

class ReportGenerator {

    public void generateWorkflowReport(int totalTasks, int completedTasks) {

        System.out.println("\n===== WORKFLOW REPORT =====");

        System.out.println("Total Tasks       : " + totalTasks);
        System.out.println("Completed Tasks   : " + completedTasks);
        System.out.println("Pending Tasks     : " + (totalTasks - completedTasks));

        if (completedTasks == totalTasks) {
            System.out.println("Workflow Status   : SUCCESS");
        } else {
            System.out.println("Workflow Status   : IN PROGRESS");
        }

        System.out.println("============================");
    }
}

public class module4 {

    public static void main(String[] args) {

        System.out.println("===== AUDIT & REPORTING MODULE =====");

        AuditManager auditManager = new AuditManager();
        ReportGenerator reportGenerator = new ReportGenerator();

        auditManager.recordEvent("Workflow WF101 created");
        auditManager.recordEvent("Task TASK101 assigned");
        auditManager.recordEvent("Task TASK101 completed");

        auditManager.displayLogs();

        reportGenerator.generateWorkflowReport(3, 2);

        System.out.println("===== End of Module 4 Simulation =====");
    }
}