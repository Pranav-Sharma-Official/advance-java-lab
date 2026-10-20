# 🎓 CollegeHub — Distributed Student Management System

A full-stack Java project that combines **all 7 lab experiment concepts** into one cohesive distributed system.

---

## 🗺️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                       CollegeHub System                         │
│                                                                 │
│  ┌──────────────────┐     TCP:6000    ┌────────────────────┐   │
│  │  Swing Desktop   │◄──────────────►│  StudentServer     │   │
│  │  Client (MVC)    │                │  (TCP Socket)      │   │
│  │  Exp 1, 2, 4     │                │  Exp 3, 4          │   │
│  └──────────────────┘                └────────┬───────────┘   │
│                                               │ JDBC           │
│  ┌──────────────────┐     RMI:1099   ┌────────▼───────────┐   │
│  │  Grade Client    │◄──────────────►│  MySQL Database    │   │
│  │  (RMI Client)    │                │  college_hub       │   │
│  │  Exp 5           │                └────────────────────┘   │
│  └──────────────────┘                                          │
│                                      ┌────────────────────┐   │
│  ┌──────────────────┐                │  GradeServer (RMI) │   │
│  │  Web Browser     │◄──────────────►│  Exp 5             │   │
│  │                  │   HTTP:8080    └────────────────────┘   │
│  └──────────────────┘                                          │
│         │ HTTP                                                  │
│  ┌──────▼───────────┐                                          │
│  │  Tomcat Server   │                                          │
│  │  Servlet + JSP   │                                          │
│  │  Exp 6, 7        │                                          │
│  └──────────────────┘                                          │
└─────────────────────────────────────────────────────────────────┘
```

---

## 📁 Project Structure

```
CollegeHub/
├── setup.sql                          ← Database setup script
├── README.md                          ← This file
│
├── desktop/                           ← Swing Desktop Client (Exp 1, 2, 4)
│   ├── Main.java                      ← Entry point
│   ├── model/
│   │   ├── Student.java               ← POJO (MVC Model)
│   │   └── StudentModel.java          ← MVC Model (socket communication)
│   ├── view/
│   │   └── StudentView.java           ← MVC View (Swing JFrame)
│   └── controller/
│       └── StudentController.java     ← MVC Controller
│
├── server/                            ← TCP Socket Server (Exp 3, 4)
│   ├── StudentServer.java             ← Multi-threaded TCP server
│   ├── DatabaseHelper.java            ← JDBC helper (PreparedStatement)
│   └── Student.java                   ← POJO (standalone copy for server)
│
├── rmi/                               ← RMI Service (Exp 5)
│   ├── GradeService.java              ← Remote interface
│   ├── GradeServiceImpl.java          ← Implementation (UnicastRemoteObject)
│   ├── GradeServer.java               ← Registers RMI service on port 1099
│   └── GradeClient.java               ← Test client
│
└── web/                               ← Maven Web App (Exp 6, 7)
    ├── pom.xml
    └── src/main/
        ├── java/com/collegehub/
        │   ├── servlet/
        │   │   ├── LoginServlet.java   ← Session management (Exp 6)
        │   │   ├── LogoutServlet.java
        │   │   ├── RegisterServlet.java← Forwards to result.jsp (Exp 7)
        │   │   └── SearchServlet.java
        │   └── filter/
        │       └── AuthFilter.java     ← Route protection (Exp 6)
        └── webapp/
            ├── index.jsp              ← Home page
            ├── login.jsp              ← Login form
            ├── register.jsp           ← Registration form (Exp 7)
            ├── result.jsp             ← Forwarded result (Exp 7)
            ├── search.jsp             ← Search/list students
            └── WEB-INF/
                └── web.xml
```

---

## 🧪 Concept Mapping

| Experiment | Concept | Where Used |
|-----------|---------|-----------|
| Exp 1 | Java Swing GUI + JDBC | `desktop/` — Swing JFrame with tabs, forms |
| Exp 2 | MVC Pattern | `desktop/model/`, `desktop/view/`, `desktop/controller/` |
| Exp 3 | JDBC + PreparedStatement | `server/DatabaseHelper.java` |
| Exp 4 | TCP Client-Server Sockets | `server/StudentServer.java` ↔ `desktop/model/StudentModel.java` |
| Exp 5 | Java RMI | `rmi/GradeService.java`, `GradeServiceImpl.java`, `GradeServer.java` |
| Exp 6 | Servlet + Session + Filter | `web/.../LoginServlet.java`, `AuthFilter.java` |
| Exp 7 | JSP + Servlet Forward | `register.jsp` → `RegisterServlet` → `result.jsp` |

---

## ⚙️ Prerequisites

- **Java JDK 17+**
- **MySQL 8.x** running on `localhost:3306`
- **MySQL Connector/J** JAR (for desktop & server compilation)
- **Apache Maven 3.8+** (for the web project)
- **Apache Tomcat 10+** (for the web project)

---

## 🚀 Setup & Running

### Step 1 — Database Setup

```sql
-- Run in MySQL Workbench or mysql CLI:
source path/to/CollegeHub/setup.sql
```

Or:
```bash
mysql -u root -p < setup.sql
```

---

### Step 2 — Start the TCP StudentServer

```bash
cd CollegeHub/server

# Download MySQL Connector/J and place it as mysql-connector.jar
javac -cp ".;mysql-connector.jar" Student.java DatabaseHelper.java StudentServer.java

java -cp ".;mysql-connector.jar" StudentServer
```

You should see:
```
╔══════════════════════════════════════════╗
║      CollegeHub — Student Server         ║
║   Listening on port 6000               ║
╚══════════════════════════════════════════╝
[Server] Ready. Waiting for clients...
```

> Keep this terminal open. The server must be running for both the desktop client and web app.

---

### Step 3 — Start the Swing Desktop Client

In a **new terminal**:

```bash
cd CollegeHub/desktop

javac -cp ".;../server/mysql-connector.jar" model/Student.java model/StudentModel.java view/StudentView.java controller/StudentController.java Main.java

java -cp ".;../server/mysql-connector.jar" Main
```

1. The Swing GUI will open.
2. Click **Connect** in the bottom bar (host: `localhost`, port: `6000`).
3. Use the **Register Student** tab to add students.
4. Use the **Search Students** tab to search or list all.

---

### Step 4 — Start the RMI Grade Server

```bash
cd CollegeHub/rmi

javac GradeService.java GradeServiceImpl.java GradeServer.java GradeClient.java

java GradeServer
```

Then test it with:
```bash
java GradeClient
```

---

### Step 5 — Build & Deploy the Web App

```bash
cd CollegeHub/web

mvn clean package

# Deploy the generated target/collegehub.war to Tomcat's webapps/ folder
# OR run with embedded Tomcat (if plugin configured):
# mvn tomcat10:run
```

Open a browser: **http://localhost:8080/collegehub**

- Default credentials: `admin` / `admin123` or `student` / `student456`
- Login, register students via the web form, and search them.

---

## 🔌 Socket Protocol Reference

The StudentServer speaks a simple pipe-delimited text protocol over TCP (port 6000):

| Command | Format | Response |
|---------|--------|----------|
| Add student | `ADD\|name\|roll\|branch\|gender\|gpa` | `OK\|message` or `ERROR\|reason` |
| Search by roll | `SEARCH\|rollNumber` | `id\|name\|roll\|branch\|gender\|gpa` or `NOT_FOUND` |
| List all | `LIST` | Multiple lines: `id\|name\|...` then `END` |
| Quit | `QUIT` | *(connection closed)* |

---

## 🎓 RMI Grade Scale

| GPA Range | Letter Grade | Remark |
|-----------|-------------|--------|
| ≥ 9.0 | O | Outstanding |
| ≥ 8.5 | A+ | Excellent |
| ≥ 8.0 | A | Very Good |
| ≥ 7.0 | B+ | Good |
| ≥ 6.0 | B | Above Average |
| ≥ 5.0 | C | Average |
| < 5.0 | F | Fail |

---

## 🔐 Security Notes

- **AuthFilter** (Experiment 6) protects `/register`, `/search`, and `/result.jsp` — any unauthenticated request is redirected to `/login.jsp`.
- Sessions are invalidated on logout and have a 30-minute timeout.
- All DB queries use **PreparedStatement** to prevent SQL injection.
- The pipe character (`|`) in user input is replaced with `-` to protect the socket protocol.

---

## 📸 Component Screenshots

| Component | Description |
|-----------|-------------|
| Desktop Client | Tabbed Swing GUI with Register + Search tabs |
| Web Home | Card-based home page with login state |
| Web Register | Form with validation, forwards to result.jsp |
| Web Search | Table with computed grade badges |

---

*Built as a demonstration project combining all Java lab experiments.*
