# Experiment 6 — Servlet Login

A web-based login system built with **Jakarta Servlets**, demonstrating session management, filter-based route protection, and the servlet lifecycle.

## What It Does

The app presents a login form. When a user submits valid credentials (`admin` / `1234`), `LoginServlet` creates an HTTP session and stores the username in it. The user is then redirected to a protected `/home` page served by `HomeServlet`. `AuthFilter` intercepts every request to `/home` and redirects unauthenticated users back to the login page. `LogoutServlet` invalidates the session and redirects back to the index.

## Technologies Used

- Jakarta Servlet 6.1.0
- Java 17
- Maven (build and dependency management)
- Apache Tomcat (or any compatible Jakarta EE 10 servlet container)

## Project Structure

```
Experiment-6_Servlet-Login/
├── pom.xml
└── src/
    └── main/
        ├── java/com/servlet/
        │   ├── LoginServlet.java     # Validates credentials, creates session
        │   ├── HomeServlet.java      # Protected page — shows welcome message
        │   ├── LogoutServlet.java    # Invalidates session, redirects to login
        │   ├── AppListener.java      # ServletContextListener for app lifecycle
        │   └── AuthFilter.java       # Filter that guards the /home route
        └── webapp/
            ├── index.html            # Login form (POSTs to /login)
            └── WEB-INF/
                └── web.xml           # (if used) Servlet/filter mappings
```

## Prerequisites

- JDK 17 or higher
- Apache Maven 3.6+
- Apache Tomcat 10.1+ (required for Jakarta EE 10 / Servlet 6.x)

## Build and Deploy

**1. Build the WAR file:**

```bash
mvn clean package
```

The WAR will be generated at `target/Experiment-6_Servlet-Login.war` (name may vary based on `pom.xml`).

**2. Deploy to Tomcat:**

Copy the WAR to Tomcat's `webapps/` directory:

```bash
cp target/*.war /path/to/tomcat/webapps/
```

Or use the Tomcat Manager web interface to upload and deploy it.

**3. Access the app:**

```
http://localhost:8080/<war-name>/
```

Log in with:
- **Username:** `admin`
- **Password:** `1234`

## Notes

- Credentials are hardcoded in `LoginServlet.java`. For a real application, replace this with a database lookup.
- `AuthFilter` is mapped to `/home`, so navigating directly to that URL without logging in will redirect you back to the index page.
