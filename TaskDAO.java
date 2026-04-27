import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public void insertTask(Task task) {
        String sql = "INSERT INTO tasks (task_id, task_name, assigned_to, state, days_pending, complexity, user_performance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.taskId);
            pstmt.setString(2, task.taskName);
            pstmt.setString(3, task.assignedTo);
            pstmt.setString(4, task.state);
            pstmt.setInt(5, task.daysPending);
            pstmt.setInt(6, task.complexity);
            pstmt.setDouble(7, task.userPerformance);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Task task = new Task(
                    rs.getString("task_id"), rs.getString("task_name"),
                    rs.getString("assigned_to"), rs.getString("state"),
                    rs.getInt("days_pending"), rs.getInt("complexity"),
                    rs.getDouble("user_performance")
                );
                task.riskScore = rs.getDouble("risk_score");
                task.recommendedAction = rs.getString("recommended_action");
                task.isHealed = rs.getBoolean("is_healed");
                tasks.add(task);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return tasks;
    }

    public Task getTaskById(String id) {
        String sql = "SELECT * FROM tasks WHERE task_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Task task = new Task(
                    rs.getString("task_id"), rs.getString("task_name"),
                    rs.getString("assigned_to"), rs.getString("state"),
                    rs.getInt("days_pending"), rs.getInt("complexity"),
                    rs.getDouble("user_performance")
                );
                task.riskScore = rs.getDouble("risk_score");
                task.recommendedAction = rs.getString("recommended_action");
                task.isHealed = rs.getBoolean("is_healed");
                return task;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET state = ?, assigned_to = ?, risk_score = ?, recommended_action = ?, is_healed = ? WHERE task_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.state);
            pstmt.setString(2, task.assignedTo);
            pstmt.setDouble(3, task.riskScore);
            pstmt.setString(4, task.recommendedAction);
            pstmt.setBoolean(5, task.isHealed);
            pstmt.setString(6, task.taskId);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}