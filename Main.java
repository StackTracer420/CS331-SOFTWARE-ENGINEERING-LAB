import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;

public class Main {

    // Global Instances
    static WorkflowRegistry workflowRegistry = new WorkflowRegistry();
    static TaskManager taskManager = new TaskManager();
    static NotificationEngine notificationEngine = new NotificationEngine();
    static StandardWorkflowEngine standardWorkflow = new StandardWorkflowEngine(notificationEngine, workflowRegistry);
    
    // AI Instances
    static AIDecisionEngine aiEngine = new AIDecisionEngine();
    static SmartAssignmentEngine smartAssign = new SmartAssignmentEngine();
    static EscalationHandler escalationHandler = new EscalationHandler(notificationEngine);
    static SelfHealingEngine selfHealing = new SelfHealingEngine(smartAssign, escalationHandler);

    public static void main(String[] args) throws IOException {
        // Define our Dynamic Workflow Rules (State -> Action -> Next State)
        workflowRegistry.addRule("DRAFT", "SUBMIT", "PENDING_REVIEW");
        workflowRegistry.addRule("PENDING_REVIEW", "APPROVE", "APPROVED");
        workflowRegistry.addRule("PENDING_REVIEW", "REJECT", "REJECTED");
        workflowRegistry.addRule("PENDING_REVIEW", "REQUEST_CHANGES", "DRAFT"); // Custom dynamic step!

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/tasks", new TasksHandler());
        server.createContext("/api/create", new CreateHandler()); // New Task Creation
        server.createContext("/api/action", new ActionHandler()); 
        server.createContext("/api/run-ai", new AIHandler());     
        server.setExecutor(null);
        server.start();
        System.out.println("🚀 [SYSTEM ONLINE] Dynamic Intelligent BPA Platform running on http://localhost:8080");
    }

    // --- API ENDPOINTS ---

    static class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json");

            List<Task> tasks = taskManager.getAllTasks();
            List<String> notifications = notificationEngine.getRecentNotifications();

            StringBuilder json = new StringBuilder("{ \"tasks\": [");
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                
                // Dynamically fetch allowed actions for this task's current state
                List<String> allowedActions = workflowRegistry.getAllowedActions(t.state);
                String actionsJson = "[\"" + String.join("\",\"", allowedActions) + "\"]";
                if (allowedActions.isEmpty()) actionsJson = "[]";

                json.append(String.format(
                    "{\"taskId\":\"%s\", \"taskName\":\"%s\", \"assignedTo\":\"%s\", \"state\":\"%s\", \"daysPending\":%d, \"riskScore\":%f, \"recommendedAction\":\"%s\", \"availableActions\":%s}",
                    t.taskId, t.taskName, t.assignedTo, t.state, t.daysPending, t.riskScore, t.recommendedAction, actionsJson
                ));
                if (i < tasks.size() - 1) json.append(",");
            }
            json.append("], \"notifications\": [");
            for (int i = 0; i < notifications.size(); i++) {
                json.append("\"").append(notifications.get(i)).append("\"");
                if (i < notifications.size() - 1) json.append(",");
            }
            json.append("]}");

            sendResponse(exchange, json.toString());
        }
    }

    static class CreateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            String query = exchange.getRequestURI().getQuery();
            if (query != null && query.contains("name") && query.contains("user")) {
                String name = query.split("name=")[1].split("&")[0].replace("%20", " ");
                String user = query.split("user=")[1];
                taskManager.addTask(name, user);
            }
            sendResponse(exchange, "{\"status\":\"created\"}");
        }
    }

    static class ActionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            String query = exchange.getRequestURI().getQuery();
            if (query != null && query.contains("taskId") && query.contains("action")) {
                String taskId = query.split("taskId=")[1].split("&")[0];
                String action = query.split("action=")[1];
                Task task = taskManager.getTaskById(taskId);
                if (task != null) standardWorkflow.processAction(task, action);
            }
            sendResponse(exchange, "{\"status\":\"success\"}");
        }
    }

    static class AIHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            for (Task t : taskManager.getAllTasks()) {
                if (!t.state.equals("APPROVED") && !t.state.equals("REJECTED")) {
                    double risk = aiEngine.predictDelay(t);
                    t.riskScore = risk;
                    t.recommendedAction = aiEngine.recommendAction(risk);
                    selfHealing.takeAction(t, t.recommendedAction, taskManager.getUserPerformances());
                }
            }
            sendResponse(exchange, "{\"status\":\"ai_completed\"}");
        }
    }

    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    // --- PHASE 1: CORE & DYNAMIC WORKFLOW ---

    static class WorkflowRegistry {
        // Maps CurrentState -> (Action -> NextState)
        private Map<String, Map<String, String>> rules = new HashMap<>();

        public void addRule(String currentState, String action, String nextState) {
            rules.putIfAbsent(currentState, new HashMap<>());
            rules.get(currentState).put(action, nextState);
        }

        public List<String> getAllowedActions(String currentState) {
            return rules.containsKey(currentState) ? new ArrayList<>(rules.get(currentState).keySet()) : new ArrayList<>();
        }

        public String getNextState(String currentState, String action) {
            if (rules.containsKey(currentState) && rules.get(currentState).containsKey(action)) {
                return rules.get(currentState).get(action);
            }
            return currentState;
        }
    }

    static class StandardWorkflowEngine {
        private NotificationEngine notifEngine;
        private WorkflowRegistry registry;

        public StandardWorkflowEngine(NotificationEngine ne, WorkflowRegistry reg) { 
            this.notifEngine = ne; 
            this.registry = reg;
        }
        
        public void processAction(Task task, String action) {
            String newState = registry.getNextState(task.state, action);
            if (!newState.equals(task.state)) {
                task.state = newState;
                task.riskScore = 0.0; // Reset AI risk on human action
                task.recommendedAction = "None";
                notifEngine.send(task.assignedTo, "Task " + task.taskId + " moved to " + newState + " via " + action);
            }
        }
    }

    static class NotificationEngine {
        private List<String> logs = new ArrayList<>();
        public void send(String user, String message) {
            String notif = "📩 [" + user + "] " + message;
            logs.add(0, notif);
            System.out.println(notif);
        }
        public List<String> getRecentNotifications() { return logs.size() > 6 ? logs.subList(0, 6) : logs; }
    }

    // --- PHASE 2: AI & SELF-HEALING MODULES ---

    static class AIDecisionEngine {
        public double predictDelay(Task task) {
            double score = 0;
            if(task.daysPending > 2) score += 0.4;
            if(task.complexity > 7) score += 0.3;
            if(task.userPerformance < 0.6) score += 0.3;
            return Math.min(score, 1.0);
        }
        public String recommendAction(double risk) {
            if(risk > 0.7) return "ESCALATE";
            else if(risk > 0.5) return "REASSIGN";
            else return "MONITOR";
        }
    }

    static class EscalationHandler {
        private NotificationEngine notifEngine;
        public EscalationHandler(NotificationEngine ne) { this.notifEngine = ne; }
        public void escalate(Task task) {
            task.assignedTo = "Senior Director";
            task.state = "ESCALATED";
            notifEngine.send("Senior Director", "🚨 AI ESCALATION: Task " + task.taskId + " critically delayed.");
        }
    }

    static class SmartAssignmentEngine {
        public String assignBestUser(Map<String, Double> userStats) {
            return userStats.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        }
    }

    static class SelfHealingEngine {
        private SmartAssignmentEngine smartAssign;
        private EscalationHandler escHandler;

        public SelfHealingEngine(SmartAssignmentEngine sa, EscalationHandler eh) {
            this.smartAssign = sa; this.escHandler = eh;
        }

        public void takeAction(Task task, String action, Map<String, Double> stats) {
            if (task.isHealed) return; 
            
            if (action.equals("REASSIGN")) {
                String bestUser = smartAssign.assignBestUser(stats);
                task.assignedTo = bestUser + " (AI)";
                task.isHealed = true;
                Main.notificationEngine.send(bestUser, "🔄 AI reassigned Task " + task.taskId + " to you.");
            } else if (action.equals("ESCALATE")) {
                escHandler.escalate(task);
                task.isHealed = true;
            }
        }
    }

    // --- DATA & STATE ---

    static class TaskManager {
        private List<Task> database;
        private Map<String, Double> userPerformances;
        private int taskCounter = 105;

        public TaskManager() {
            database = new ArrayList<>(Arrays.asList(
                new Task("TSK-101", "Vendor Payment", "John", "DRAFT", 0, 3, 0.4),
                new Task("TSK-102", "Server Audit", "Alice", "PENDING_REVIEW", 1, 4, 0.9),
                new Task("TSK-103", "Data Migration", "Bob", "PENDING_REVIEW", 3, 8, 0.3)
            ));
            userPerformances = new HashMap<>();
            userPerformances.put("John", 0.4); userPerformances.put("Bob", 0.3);
            userPerformances.put("Alice", 0.9); userPerformances.put("Sarah_TopPerformer", 0.99);
        }

        public void addTask(String name, String user) {
            String id = "TSK-" + (taskCounter++);
            database.add(new Task(id, name, user, "DRAFT", 0, 5, 0.5));
            Main.notificationEngine.send("System", "New task created: " + name);
        }

        public List<Task> getAllTasks() { return database; }
        public Task getTaskById(String id) { return database.stream().filter(t -> t.taskId.equals(id)).findFirst().orElse(null); }
        public Map<String, Double> getUserPerformances() { return userPerformances; }
    }

    static class Task {
        String taskId, taskName, assignedTo, state, recommendedAction;
        int daysPending, complexity;
        double userPerformance, riskScore;
        boolean isHealed;

        public Task(String id, String name, String user, String state, int days, int comp, double perf) {
            this.taskId = id; this.taskName = name; this.assignedTo = user; this.state = state;
            this.daysPending = days; this.complexity = comp; this.userPerformance = perf;
            this.riskScore = 0.0; this.recommendedAction = "None"; this.isHealed = false;
        }
    }
}