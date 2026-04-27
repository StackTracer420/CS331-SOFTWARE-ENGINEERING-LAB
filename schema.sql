CREATE DATABASE bpa_platform_db;
USE bpa_platform_db;

CREATE TABLE tasks (
    task_id VARCHAR(50) PRIMARY KEY,
    task_name VARCHAR(100) NOT NULL,
    assigned_to VARCHAR(50) NOT NULL,
    state VARCHAR(30) DEFAULT 'DRAFT',
    days_pending INT DEFAULT 0,
    complexity INT DEFAULT 5,
    user_performance DOUBLE DEFAULT 0.5,
    risk_score DOUBLE DEFAULT 0.0,
    recommended_action VARCHAR(50) DEFAULT 'None',
    is_healed BOOLEAN DEFAULT FALSE
);

-- Insert initial mock data so your dashboard isn't empty!
INSERT INTO tasks (task_id, task_name, assigned_to, state, days_pending, complexity, user_performance) VALUES 
('TSK-101', 'Vendor Payment', 'John', 'DRAFT', 0, 3, 0.4),
('TSK-102', 'Server Audit', 'Alice', 'PENDING_REVIEW', 1, 4, 0.9),
('TSK-103', 'Data Migration', 'Bob', 'PENDING_REVIEW', 3, 8, 0.3);