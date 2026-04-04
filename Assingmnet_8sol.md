# CS 331 – Software Engineering Lab

## Assignment 8 Report

---
# Part A: Data Access Layer (DAL)

## 1. Introduction

The Data Access Layer (DAL) acts as an abstraction between the application logic and the database. It ensures that database operations are handled separately from business logic, improving maintainability and scalability.

---

## 2. Database Design

### Database Name: BPA_System

### Users Collection

```json
{
  "_id": "ObjectId",
  "name": "string",
  "email": "string",
  "password": "string",
  "role": "Admin | Manager | Employee",
  "createdAt": "Date"
}
```

### Workflows Collection

```json
{
  "_id": "ObjectId",
  "title": "string",
  "description": "string",
  "createdBy": "userId",
  "createdAt": "Date"
}
```

### Tasks Collection

```json
{
  "_id": "ObjectId",
  "workflowId": "ObjectId",
  "assignedTo": "userId",
  "status": "Pending | Active | Completed",
  "deadline": "Date",
  "createdAt": "Date"
}
```

### Logs Collection

```json
{
  "_id": "ObjectId",
  "taskId": "ObjectId",
  "action": "string",
  "timestamp": "Date"
}
```

---

## 3. DAL Implementation

### Database Connection

```js
const mongoose = require('mongoose');

mongoose.connect("mongodb://localhost:27017/BPA_System", {
    useNewUrlParser: true,
    useUnifiedTopology: true
});

module.exports = mongoose;
```

### User Repository

```js
const User = require('../models/User');

async function createUser(data) {
    return await User.create(data);
}

async function getUserByEmail(email) {
    return await User.findOne({ email });
}

async function getAllUsers() {
    return await User.find();
}

module.exports = {
    createUser,
    getUserByEmail,
    getAllUsers
};
```

### Task Repository

```js
const Task = require('../models/Task');

async function createTask(data) {
    return await Task.create(data);
}

async function updateTaskStatus(taskId, status) {
    return await Task.findByIdAndUpdate(taskId, { status });
}

async function getTasksByUser(userId) {
    return await Task.find({ assignedTo: userId });
}

module.exports = {
    createTask,
    updateTaskStatus,
    getTasksByUser
};
```

---

## 4. DAL Features

* Provides abstraction over database operations
* Uses Repository Pattern
* Improves maintainability and scalability
* Separates business logic from data handling

---

# Part B: Testing

## 1. White Box Testing

White Box Testing focuses on internal code structure and logic.

### Test Cases

| Test Case ID | Function           | Input          | Expected Output |
| ------------ | ------------------ | -------------- | --------------- |
| WB1          | createUser()       | Valid data     | User created    |
| WB2          | createUser()       | Missing email  | Error           |
| WB3          | getUserByEmail()   | Existing email | User returned   |
| WB4          | updateTaskStatus() | Valid ID       | Status updated  |
| WB5          | updateTaskStatus() | Invalid ID     | Error           |

### Example Code

```js
test("Create User", async () => {
    const user = await createUser({
        name: "John",
        email: "john@test.com",
        password: "123456",
        role: "Employee"
    });

    expect(user.email).toBe("john@test.com");
});
```

---

## 2. Black Box Testing

Black Box Testing evaluates system functionality without internal knowledge.

### Test Cases

| Test Case ID | Feature     | Input               | Expected Output |
| ------------ | ----------- | ------------------- | --------------- |
| BB1          | Login       | Correct credentials | Success         |
| BB2          | Login       | Wrong password      | Error           |
| BB3          | Create Task | Valid data          | Task created    |
| BB4          | Create Task | Missing data        | Error           |
| BB5          | View Tasks  | Valid user          | Task list       |

### Manual Testing Steps

| Step | Action            | Result        |
| ---- | ----------------- | ------------- |
| 1    | Open login page   | Page loads    |
| 2    | Enter credentials | Login success |
| 3    | Create task       | Task added    |

---

## 3. Testing Summary

| Testing Type | Focus         | Result |
| ------------ | ------------- | ------ |
| White Box    | Code logic    | Passed |
| Black Box    | Functionality | Passed |

---

# Conclusion

The Data Access Layer was successfully implemented using a structured repository approach. The system ensures proper separation of concerns, and both White Box and Black Box testing confirm that the application is reliable and functional.
