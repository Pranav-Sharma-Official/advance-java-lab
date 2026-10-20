# Experiment 3 — JDBC Connectivity

A minimal Java program that demonstrates how to connect to a MySQL database using JDBC and perform a basic insert operation with a `PreparedStatement`.

## What It Does

The application connects to a local MySQL `college` database and inserts a student record (name and branch) into the `student` table. It's intentionally kept simple — the goal is to illustrate the core JDBC workflow: load driver → open connection → prepare statement → execute → close.

## Technologies Used

- Java JDBC API
- MySQL Connector/J (`mysql-connector-j-26.7.0.jar`)
- MySQL

## Project Structure

```
Experiment-3_JDBC-Connectivity/
├── src/
│   └── App.java       # Main program
├── lib/
│   └── mysql-connector-j-26.7.0.jar
└── bin/               # Compiled .class files go here
```

## Prerequisites

- JDK 8 or higher
- MySQL Server running on `localhost:3306`
- A database and table set up as follows:

```sql
CREATE DATABASE IF NOT EXISTS college;

USE college;

CREATE TABLE student (
    id     INT AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(100),
    branch VARCHAR(100)
);
```

## How to Run

From the experiment root directory:

1. **Compile:**

```bash
javac -cp "lib/mysql-connector-j-26.7.0.jar" -d bin src/App.java
```

2. **Run:**

```bash
java -cp "bin;lib/mysql-connector-j-26.7.0.jar" App
```

> On Linux/macOS, replace `;` with `:` in the classpath.

If the connection succeeds and the insert runs without errors, the record will appear in the `student` table.
