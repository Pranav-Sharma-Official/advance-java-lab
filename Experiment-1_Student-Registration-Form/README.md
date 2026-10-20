# Experiment 1 — Student Registration Form

A Java Swing desktop application that provides a GUI form for registering student records into a MySQL database.

## What It Does

The form collects a student's Name, Roll Number, Branch, Gender (via radio buttons), and requires the user to accept Terms & Conditions via a checkbox before submission. On clicking **Submit**, the app validates the inputs and inserts the record into the `college.student` table. A **Reset** button clears all fields back to their default state.

## Technologies Used

- Java Swing (`JFrame`, `JLabel`, `JTextField`, `JRadioButton`, `JCheckBox`, `JButton`)
- JDBC for database connectivity
- MySQL as the backend database

## Prerequisites

- JDK 8 or higher
- MySQL Server running locally on port `3306`
- MySQL Connector/J JAR on the classpath
- A database named `college` with the following table:

```sql
CREATE DATABASE IF NOT EXISTS college;

USE college;

CREATE TABLE student (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100),
    roll_number VARCHAR(50),
    branch      VARCHAR(100),
    gender      VARCHAR(10)
);
```

## How to Run

1. **Compile** the source file (make sure the MySQL connector JAR is included):

```bash
javac -cp ".;path/to/mysql-connector.jar" Gaurav.java
```

2. **Run** the application:

```bash
java -cp ".;path/to/mysql-connector.jar" Gaurav
```

The registration form window will open. Fill in the details, accept the terms, and click **Submit** to save the record.

## Project Structure

```
Experiment-1_Student-Registration-Form/
└── Gaurav.java        # Main application file (UI + JDBC logic)
```

## Database Connection

The app connects to:

```
jdbc:mysql://127.0.0.1:3306/college
```

Update the username and password constants inside `Gaurav.java` if your MySQL credentials differ from the defaults.
