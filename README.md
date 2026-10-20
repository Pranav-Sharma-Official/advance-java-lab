# Java Experiments

A collection of seven Java experiments covering core topics in desktop GUI programming, database connectivity, network programming, and Java web development. Each experiment lives in its own folder and is self-contained.

**Student:** `PRANAV SHARMA`
**Enrollment:** `24EGJCC015`

## Experiments

| # | Experiment | Key Technologies |
|---|---|---|
| 1 | [Student Registration Form](./Experiment-1_Student-Registration-Form/) | Java Swing, JDBC, MySQL |
| 2 | [MVC Calculator](./Experiment-2_MVC-Calculator/) | Java Swing, MVC Pattern |
| 3 | [JDBC Connectivity](./Experiment-3_JDBC-Connectivity/) | JDBC, MySQL |
| 4 | [Client-Server Architecture](./Experiment-4_Client-Server-Architecture/) | Java Sockets, TCP |
| 5 | [RMI Calculator](./Experiment-5_RMI-Calculator/) | Java RMI |
| 6 | [Servlet Login](./Experiment-6_Servlet-Login/) | Jakarta Servlet, HttpSession, Maven |
| 7 | [JSP Conversion](./Experiment-7_JSP-Conversion/) | Jakarta Servlet, JSP, EL, Maven |

---

## Brief Descriptions

### 1. Student Registration Form
A Swing desktop form for registering students. Collects Name, Roll No, Branch, Gender, and Terms acceptance, then inserts the record into a MySQL database via JDBC.

### 2. MVC Calculator
A calculator app that cleanly separates concerns using the Model-View-Controller pattern. The Model handles math, the View handles the Swing UI, and the Controller wires them together.

### 3. JDBC Connectivity
A minimal example of connecting Java to MySQL with JDBC and inserting a record using `PreparedStatement`. Good starting point for understanding raw JDBC before using ORMs.

### 4. Client-Server Architecture
A TCP socket demo where a server listens on port 5000 and a client sends it a message, then prints the server's reply. Uses `ServerSocket`, `Socket`, `BufferedReader`, and `PrintWriter`.

### 5. RMI Calculator
A distributed calculator using Java RMI. The server registers a remote object; the client looks it up and calls `add` and `subtract` methods as if they were local.

### 6. Servlet Login
A web login system built with Jakarta Servlets. Demonstrates credential validation, `HttpSession` management, filter-based route protection (`AuthFilter`), and logout handling.

### 7. JSP Conversion
Shows the Servlet-as-controller, JSP-as-view pattern. A student form POSTs to a servlet, which sets request attributes and forwards to a JSP that renders them with Expression Language.

---

## General Prerequisites

| Requirement | Details |
|---|---|
| JDK | 17+ recommended (8+ for experiments 1–5) |
| MySQL | Required for experiments 1 and 3 |
| Apache Maven | Required for experiments 6 and 7 |
| Apache Tomcat | 10.1+ required for experiments 6 and 7 |

Each experiment folder contains its own `README.md` with specific setup instructions, build commands, and database schema where applicable.
