import javax.swing.*;
import java.awt.*;

public class BPA_GUI {

    private module1.Workflow workflow;
    private module1.Task task1;
    private module1.Task task2;

    private EscalationHandler escalation;
    private AuditManager audit;

    private JTextArea logArea;
    private JLabel workflowStatus;

    public BPA_GUI() {

        workflow = new module1.Workflow("WF101", "Document Approval");
        task1 = new module1.Task("T1", "Prepare Document");
        task2 = new module1.Task("T2", "Manager Approval");

        workflow.addTask(task1);
        workflow.addTask(task2);

        escalation = new EscalationHandler();
        audit = new AuditManager();
        JFrame frame = new JFrame("BPA Workflow Automation System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JLabel title = new JLabel("Business Process Automation System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        frame.add(title, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton startWorkflow = new JButton("Start Workflow");
        JButton assignTasks = new JButton("Assign Tasks");
        JButton completeTasks = new JButton("Complete Tasks");
        JButton checkEscalation = new JButton("Check Escalation");
        JButton viewDetails = new JButton("Show Workflow Details");

        buttonPanel.add(startWorkflow);
        buttonPanel.add(assignTasks);
        buttonPanel.add(completeTasks);
        buttonPanel.add(checkEscalation);
        buttonPanel.add(viewDetails);

        frame.add(buttonPanel, BorderLayout.WEST);
        logArea = new JTextArea();
        logArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(logArea);

        frame.add(scrollPane, BorderLayout.CENTER);
        JPanel statusPanel = new JPanel();
        workflowStatus = new JLabel("Workflow Status: Not Started");

        statusPanel.add(workflowStatus);

        frame.add(statusPanel, BorderLayout.SOUTH);

        startWorkflow.addActionListener(e -> {

            workflow.startWorkflow();
            workflowStatus.setText("Workflow Status: In Progress");

            log("Workflow started.");
            audit.recordEvent("Workflow Started");
        });

        assignTasks.addActionListener(e -> {

            task1.assignTask("Employee01");
            task2.assignTask("Manager01");

            log("Tasks assigned to users.");

            audit.recordEvent("Tasks Assigned");
        });

        completeTasks.addActionListener(e -> {

            task1.completeTask();
            task2.completeTask();

            workflow.completeWorkflow();

            workflowStatus.setText("Workflow Status: Completed");

            log("Tasks completed and workflow finished.");

            audit.recordEvent("Tasks Completed");
        });

        checkEscalation.addActionListener(e -> {

            Task delayedTask = new Task("T2", "Pending", 5);

            escalation.checkAndEscalate(delayedTask, "Manager01");

            log("Escalation check performed.");

            audit.recordEvent("Escalation Checked");
        });

        viewDetails.addActionListener(e -> {

            workflow.displayWorkflow();
            audit.displayLogs();

            log("Workflow details printed in console.");
        });

        frame.setVisible(true);
    }

    private void log(String message) {

        logArea.append(message + "\n");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new BPA_GUI_Enhanced());
    }
}