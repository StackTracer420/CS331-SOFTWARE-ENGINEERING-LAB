public class Task {
    public String taskId, taskName, assignedTo, state, recommendedAction;
    public int daysPending, complexity;
    public double userPerformance, riskScore;
    public boolean isHealed;

    public Task(String id, String name, String user, String state, int days, int comp, double perf) {
        this.taskId = id; 
        this.taskName = name; 
        this.assignedTo = user; 
        this.state = state;
        this.daysPending = days; 
        this.complexity = comp; 
        this.userPerformance = perf;
        this.riskScore = 0.0; 
        this.recommendedAction = "None"; 
        this.isHealed = false;
    }
}