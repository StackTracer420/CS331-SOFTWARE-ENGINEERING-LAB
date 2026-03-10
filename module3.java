import java.util.ArrayList;
import java.util.List;

class User {

    private String userId;
    private String name;
    private String role;

    public User(String userId, String name, String role) {
        this.userId = userId;
        this.name = name;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public void displayUser() {
        System.out.println("User ID : " + userId);
        System.out.println("Name    : " + name);
        System.out.println("Role    : " + role);
        System.out.println("-----------------------------");
    }
}

class RoleManager {

    private List<User> users;

    public RoleManager() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("User added: " + user.getUserId());
    }

    public User findUser(String userId) {
        for (User u : users) {
            if (u.getUserId().equals(userId)) {
                return u;
            }
        }
        return null;
    }

    public void showAllUsers() {
        System.out.println("\n===== USER LIST =====");
        for (User u : users) {
            u.displayUser();
        }
    }
}

class TaskAssignmentService {

    public void assignTaskToUser(String taskId, User user) {

        if (user == null) {
            System.out.println("User not found. Cannot assign task.");
            return;
        }

        System.out.println("Task " + taskId + " assigned to user " + user.getUserId()
                + " with role " + user.getRole());
    }
}

public class module3 {

    public static void main(String[] args) {

        System.out.println("===== USER MANAGEMENT MODULE =====");

        RoleManager roleManager = new RoleManager();
        TaskAssignmentService assignmentService = new TaskAssignmentService();

        User u1 = new User("U101", "Alice", "Employee");
        User u2 = new User("U102", "Bob", "Manager");

        roleManager.addUser(u1);
        roleManager.addUser(u2);

        roleManager.showAllUsers();

        User assignedUser = roleManager.findUser("U101");

        assignmentService.assignTaskToUser("TASK101", assignedUser);

        System.out.println("===== End of Module 3 Simulation =====");
    }
}