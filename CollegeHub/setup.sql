-- CollegeHub Database Setup Script
-- Experiment 3: JDBC with PreparedStatement

CREATE DATABASE IF NOT EXISTS college_hub;
USE college_hub;

CREATE TABLE IF NOT EXISTS students (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    roll_number VARCHAR(50)   NOT NULL UNIQUE,
    branch      VARCHAR(50)   NOT NULL,
    gender      VARCHAR(10)   NOT NULL,
    gpa         DOUBLE        NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sample data for quick testing
INSERT IGNORE INTO students (name, roll_number, branch, gender, gpa) VALUES
  ('Alice Sharma',    'CS2021001', 'Computer Science', 'Female', 9.2),
  ('Bob Patel',       'CS2021002', 'Computer Science', 'Male',   8.5),
  ('Carol Mehta',     'EC2021001', 'Electronics',      'Female', 7.8),
  ('David Rao',       'ME2021001', 'Mechanical',       'Male',   8.0);
