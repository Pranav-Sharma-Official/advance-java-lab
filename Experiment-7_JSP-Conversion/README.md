# Experiment 7 — JSP Conversion

A Maven web application demonstrating how a **Java Servlet** can process form data and forward it to a **JSP page** for rendering, using Expression Language (EL).

## What It Does

`student.jsp` displays a form where a user fills in their Name, Email, Course, and Age. The form POSTs to the `/hello` endpoint handled by `HelloServlet`. The servlet reads each parameter, sets them as request attributes, and forwards the request to `result.jsp`. The result page then displays the submitted data using EL expressions like `${name}` and `${email}` — no scriptlets required.

## Technologies Used

- Jakarta Servlet 6.1.0
- Jakarta Server Pages (JSP) 4.0.0
- Expression Language (EL)
- Java 17
- Maven

## Project Structure

```
Experiment-7_JSP-Conversion/
├── pom.xml
└── src/
    └── main/
        ├── java/com/servlet/
        │   └── HelloServlet.java     # Reads form params, sets attributes, forwards to JSP
        └── webapp/
            ├── student.jsp           # Input form (Name, Email, Course, Age)
            └── result.jsp            # Displays submitted data via EL
```

## Prerequisites

- JDK 17 or higher
- Apache Maven 3.6+
- Apache Tomcat 10.1+ (Jakarta EE 10 compatible)

## Build and Deploy

**1. Build the WAR:**

```bash
mvn clean package
```

**2. Deploy to Tomcat:**

```bash
cp target/*.war /path/to/tomcat/webapps/
```

**3. Open the app in a browser:**

```
http://localhost:8080/<war-name>/student.jsp
```

Fill in the form and submit — the result page will display everything you entered.

## How the Forwarding Works

```
Browser → POST /hello
          └─▶ HelloServlet
                  reads params
                  sets request attributes
                  request.getRequestDispatcher("result.jsp").forward(...)
                        └─▶ result.jsp renders the attributes via EL
```

This pattern (Servlet as controller, JSP as view) is the foundation of the MVC approach commonly used in traditional Java web development before frameworks like Spring MVC became standard.
