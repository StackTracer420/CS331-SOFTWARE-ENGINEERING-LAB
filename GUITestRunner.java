import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class GUITestRunner extends JFrame {

    // UI
    private JTextPane consoleOutput;
    private StyledDocument doc;
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private JLabel summaryLabel;
    private JButton runBtn, clearBtn, saveBtn;

    // Theme
    private static final Color BG = new Color(18, 18, 22);
    private static final Color PANEL = new Color(24, 24, 29);
    private static final Color PANEL_2 = new Color(30, 30, 36);
    private static final Color TEXT = new Color(220, 220, 225);
    private static final Color MUTED = new Color(150, 150, 160);
    private static final Color ACCENT = new Color(88, 166, 255);
    private static final Color GOOD = new Color(63, 185, 80);
    private static final Color BAD = new Color(248, 81, 73);
    private static final Color WARN = new Color(210, 153, 34);

    public GUITestRunner() {
        setTitle("Intelligent BPA — Test Suite Runner");
        setSize(880, 560);
        setMinimumSize(new Dimension(820, 520));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Root
        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBorder(new EmptyBorder(14, 14, 14, 14));
        root.setBackground(BG);
        setContentPane(root);

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildCenter(), BorderLayout.CENTER);
        root.add(buildFooter(), BorderLayout.SOUTH);

        applyConsoleStyles();
        setStatus("Ready", MUTED);
        setSummary("0 passed • 0 failed • 0 total");
    }

    private JComponent buildHeader() {
        JPanel header = new JPanel(new BorderLayout(12, 12));
        header.setBackground(PANEL);
        header.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel titles = new JPanel();
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));
        titles.setOpaque(false);

        JLabel title = new JLabel("Predictive AI & Self‑Healing Module Tests");
        title.setForeground(TEXT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel subtitle = new JLabel("Run the full suite and review results with live progress and a saved report option.");
        subtitle.setForeground(MUTED);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        titles.add(title);
        titles.add(Box.createVerticalStrut(4));
        titles.add(subtitle);

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        statusLabel = new JLabel("Ready");
        statusLabel.setOpaque(true);
        statusLabel.setBorder(new EmptyBorder(6, 10, 6, 10));
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setForeground(TEXT);
        statusLabel.setBackground(PANEL_2);
        statusLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        right.add(statusLabel);

        header.add(titles, BorderLayout.CENTER);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    private JComponent buildCenter() {
        JPanel center = new JPanel(new BorderLayout(14, 14));
        center.setOpaque(false);

        // Left control panel
        JPanel controls = new JPanel();
        controls.setBackground(PANEL);
        controls.setBorder(new EmptyBorder(14, 14, 14, 14));
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));

        JLabel controlTitle = new JLabel("Controls");
        controlTitle.setForeground(TEXT);
        controlTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));

        runBtn = createButton("Run 8 Test Cases", ACCENT);
        clearBtn = createButton("Clear Output", new Color(120, 120, 130));
        saveBtn = createButton("Save Log…", new Color(160, 120, 255));

        runBtn.addActionListener(e -> runAllTestsAsync());
        clearBtn.addActionListener(e -> clearConsole());
        saveBtn.addActionListener(e -> saveLogToFile());

        JLabel hints = new JLabel("<html><body style='width:220px'>"
                + "<span style='color:#cfcfd6'>Tip:</span> "
                + "<span style='color:#9a9aa3'>Use Save Log to export results as a .txt report.</span>"
                + "</body></html>");
        hints.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        controls.add(controlTitle);
        controls.add(Box.createVerticalStrut(10));
        controls.add(runBtn);
        controls.add(Box.createVerticalStrut(8));
        controls.add(clearBtn);
        controls.add(Box.createVerticalStrut(8));
        controls.add(saveBtn);
        controls.add(Box.createVerticalStrut(16));
        controls.add(new JSeparator());
        controls.add(Box.createVerticalStrut(12));
        controls.add(hints);
        controls.add(Box.createVerticalGlue());

        // Console output
        JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.setBackground(PANEL);
        consolePanel.setBorder(new EmptyBorder(14, 14, 14, 14));

        JLabel consoleTitle = new JLabel("Test Results");
        consoleTitle.setForeground(TEXT);
        consoleTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        consoleTitle.setBorder(new EmptyBorder(0, 0, 10, 0));

        consoleOutput = new JTextPane();
        consoleOutput.setEditable(false);
        consoleOutput.setBackground(PANEL_2);
        consoleOutput.setForeground(TEXT);
        consoleOutput.setFont(new Font("Consolas", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(consoleOutput);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(45, 45, 55), 1));
        scroll.getViewport().setBackground(PANEL_2);

        consolePanel.add(consoleTitle, BorderLayout.NORTH);
        consolePanel.add(scroll, BorderLayout.CENTER);

        center.add(controls, BorderLayout.WEST);
        center.add(consolePanel, BorderLayout.CENTER);

        return center;
    }

    private JComponent buildFooter() {
        JPanel footer = new JPanel(new BorderLayout(12, 10));
        footer.setBackground(PANEL);
        footer.setBorder(new EmptyBorder(10, 14, 10, 14));

        progressBar = new JProgressBar(0, 8);
        progressBar.setStringPainted(true);
        progressBar.setForeground(ACCENT);
        progressBar.setBackground(PANEL_2);
        progressBar.setBorderPainted(false);
        progressBar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        progressBar.setValue(0);
        progressBar.setString("0 / 8");

        summaryLabel = new JLabel("0 passed • 0 failed • 0 total");
        summaryLabel.setForeground(MUTED);
        summaryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        footer.add(progressBar, BorderLayout.CENTER);
        footer.add(summaryLabel, BorderLayout.EAST);
        return footer;
    }

    private JButton createButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setBorder(new EmptyBorder(10, 14, 10, 14));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void applyConsoleStyles() {
        doc = consoleOutput.getStyledDocument();

        Style base = doc.addStyle("base", null);
        StyleConstants.setFontFamily(base, "Consolas");
        StyleConstants.setFontSize(base, 13);
        StyleConstants.setForeground(base, TEXT);

        Style info = doc.addStyle("info", base);
        StyleConstants.setForeground(info, new Color(190, 190, 200));

        Style pass = doc.addStyle("pass", base);
        StyleConstants.setForeground(pass, GOOD);
        StyleConstants.setBold(pass, true);

        Style fail = doc.addStyle("fail", base);
        StyleConstants.setForeground(fail, BAD);
        StyleConstants.setBold(fail, true);

        Style warn = doc.addStyle("warn", base);
        StyleConstants.setForeground(warn, WARN);
        StyleConstants.setBold(warn, true);

        Style mono = doc.addStyle("mono", base);
        StyleConstants.setForeground(mono, new Color(210, 210, 220));
    }

    // ---------- Logging ----------
    private void clearConsole() {
        consoleOutput.setText("");
        progressBar.setValue(0);
        progressBar.setString("0 / 8");
        setSummary("0 passed • 0 failed • 0 total");
        setStatus("Ready", MUTED);
    }

    private void logLine(String style, String message) {
        try {
            doc.insertString(doc.getLength(), message + "\n", doc.getStyle(style));
            consoleOutput.setCaretPosition(doc.getLength());
        } catch (BadLocationException ignored) { }
    }

    private void logTag(String tag, String style, String message) {
        logLine("mono", String.format("[%s] ", tag));
        logLine(style, message);
    }

    private void setStatus(String text, Color c) {
        statusLabel.setText(text);
        statusLabel.setBackground(new Color(38, 38, 46));
        statusLabel.setForeground(c);
    }

    private void setSummary(String text) {
        summaryLabel.setText(text);
    }

    private void setRunning(boolean running) {
        runBtn.setEnabled(!running);
        clearBtn.setEnabled(!running);
        saveBtn.setEnabled(!running);
    }

    private void updateProgress(int done, int total) {
        progressBar.setMaximum(total);
        progressBar.setValue(done);
        progressBar.setString(done + " / " + total);
    }

    // ---------- Async execution ----------
    private void runAllTestsAsync() {
        clearConsole();
        setRunning(true);
        setStatus("Running…", ACCENT);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            int passed = 0;
            int failed = 0;
            int done = 0;
            final int total = 8;

            @Override
            protected Void doInBackground() {
                logLine("info", "=========================================");
                logLine("info", "Initializing test environment…");
                logLine("info", "=========================================\n");

                // Run tests but keep UI responsive
                TestResult r;

                r = tcAI01(); record(r);
                r = tcAI02(); record(r);
                r = tcAI03(); record(r);
                r = tcAI04(); record(r);
                r = tcAI05(); record(r);
                r = tcAI06(); record(r);
                r = tcSH01(); record(r);
                r = tcSH02(); record(r);

                logLine("info", "\n=========================================");
                logLine("info", "Test execution completed.");
                return null;
            }

            private void record(TestResult r) {
                done++;
                if (r.pass) passed++; else failed++;

                SwingUtilities.invokeLater(() -> {
                    if (r.pass) {
                        logLine("pass", "[PASS] " + r.name + " — " + r.details);
                    } else {
                        logLine("fail", "[FAIL] " + r.name + " — " + r.details);
                    }
                    updateProgress(done, total);
                    setSummary(passed + " passed • " + failed + " failed • " + total + " total");
                });

                // Small delay makes progress feel “live”; remove if you want instant output
                try { Thread.sleep(90); } catch (InterruptedException ignored) {}
            }

            @Override
            protected void done() {
                setRunning(false);
                if (failed == 0) setStatus("All tests passed", GOOD);
                else setStatus("Completed with failures", BAD);
            }

            // ---------- Your existing logic, preserved ----------
            private TestResult tcAI01() {
                Main.AIDecisionEngine ai = new Main.AIDecisionEngine();
                Task t1 = new Task("T1", "Test", "User", "DRAFT", 1, 5, 0.9);
                double r1 = ai.predictDelay(t1);
                return new TestResult("TC-AI-01 Baseline Risk", r1 == 0.0, "Expected 0.0, got " + r1);
            }

            private TestResult tcAI02() {
                Main.AIDecisionEngine ai = new Main.AIDecisionEngine();
                Task t2 = new Task("T2", "Test", "User", "DRAFT", 3, 5, 0.9);
                double r2 = ai.predictDelay(t2);
                return new TestResult("TC-AI-02 Single Penalty", Math.abs(r2 - 0.4) < 0.01, "Expected ~0.4, got " + r2);
            }

            private TestResult tcAI03() {
                Main.AIDecisionEngine ai = new Main.AIDecisionEngine();
                Task t3 = new Task("T3", "Test", "User", "DRAFT", 4, 8, 0.9);
                double r3 = ai.predictDelay(t3);
                return new TestResult("TC-AI-03 Multi-Penalty", Math.abs(r3 - 0.7) < 0.01, "Expected ~0.7, got " + r3);
            }

            private TestResult tcAI04() {
                Main.AIDecisionEngine ai = new Main.AIDecisionEngine();
                Task t4 = new Task("T4", "Test", "User", "DRAFT", 5, 9, 0.2);
                double r4 = ai.predictDelay(t4);
                return new TestResult("TC-AI-04 Upper Boundary", r4 == 1.0, "Expected 1.0, got " + r4);
            }

            private TestResult tcAI05() {
                Main.AIDecisionEngine ai = new Main.AIDecisionEngine();
                String action1 = ai.recommendAction(0.45);
                return new TestResult("TC-AI-05 Recommend MONITOR", "MONITOR".equals(action1), "Expected MONITOR, got " + action1);
            }

            private TestResult tcAI06() {
                Main.AIDecisionEngine ai = new Main.AIDecisionEngine();
                String action2 = ai.recommendAction(0.85);
                return new TestResult("TC-AI-06 Recommend ESCALATE", "ESCALATE".equals(action2), "Expected ESCALATE, got " + action2);
            }

            private TestResult tcSH01() {
                Main.NotificationEngine ne = new Main.NotificationEngine();
                Main.SmartAssignmentEngine sa = new Main.SmartAssignmentEngine();
                Main.EscalationHandler eh = new Main.EscalationHandler(ne);
                Main.SelfHealingEngine healingEngine = new Main.SelfHealingEngine(sa, eh);

                Task t7 = new Task("T7", "Heal Test", "Bob", "DRAFT", 5, 8, 0.3);
                Map<String, Double> mockStats = new HashMap<>();
                mockStats.put("Bob", 0.3);
                mockStats.put("Alice", 0.9);

                healingEngine.takeAction(t7, "REASSIGN", mockStats);
                boolean ok = t7.assignedTo.contains("Alice") && t7.isHealed;
                return new TestResult("TC-SH-01 Smart Reassignment", ok, "Expected reassigned to Alice and healed=true");
            }

            private TestResult tcSH02() {
                Main.NotificationEngine ne = new Main.NotificationEngine();
                Main.SmartAssignmentEngine sa = new Main.SmartAssignmentEngine();
                Main.EscalationHandler eh = new Main.EscalationHandler(ne);
                Main.SelfHealingEngine healingEngine = new Main.SelfHealingEngine(sa, eh);

                Task t8 = new Task("T8", "Circuit Breaker", "User", "DRAFT", 5, 8, 0.3);
                Map<String, Double> mockStats = new HashMap<>();
                mockStats.put("Bob", 0.3);
                mockStats.put("Alice", 0.9);

                t8.isHealed = true; // Force circuit breaker
                healingEngine.takeAction(t8, "ESCALATE", mockStats);
                boolean ok = !"ESCALATED".equals(t8.state);
                return new TestResult("TC-SH-02 Circuit Breaker", ok, "Expected state unchanged (not ESCALATED)");
            }
        };

        worker.execute();
    }

    private void saveLogToFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Test Log");
        chooser.setSelectedFile(new java.io.File("test-log-" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + ".txt"));

        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        try (FileWriter fw = new FileWriter(chooser.getSelectedFile())) {
            fw.write(consoleOutput.getText());
            JOptionPane.showMessageDialog(this, "Log saved successfully.",
                    "Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save log:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Small helper record class
    private static class TestResult {
        final String name;
        final boolean pass;
        final String details;

        TestResult(String name, boolean pass, String details) {
            this.name = name;
            this.pass = pass;
            this.details = details;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUITestRunner().setVisible(true));
    }
}