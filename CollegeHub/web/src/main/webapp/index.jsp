<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CollegeHub — Home</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Tahoma, sans-serif; background: #f0f4ff; color: #333; }

        .navbar {
            background: #1976d2; color: white; padding: 14px 24px;
            display: flex; justify-content: space-between; align-items: center;
            box-shadow: 0 2px 6px rgba(0,0,0,0.25);
        }
        .navbar h1 { font-size: 1.4rem; }
        .navbar a  { color: white; text-decoration: none; margin-left: 18px; font-size: 0.95rem; }
        .navbar a:hover { text-decoration: underline; }

        .hero {
            text-align: center; padding: 60px 20px 30px;
            background: linear-gradient(135deg, #1976d2, #42a5f5);
            color: white;
        }
        .hero h2 { font-size: 2.2rem; margin-bottom: 12px; }
        .hero p  { font-size: 1.1rem; opacity: 0.9; }

        .cards {
            display: flex; flex-wrap: wrap; gap: 24px;
            justify-content: center; padding: 48px 24px;
            max-width: 900px; margin: 0 auto;
        }
        .card {
            background: white; border-radius: 12px; padding: 32px 28px;
            width: 260px; text-align: center; box-shadow: 0 4px 16px rgba(0,0,0,0.1);
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .card:hover { transform: translateY(-4px); box-shadow: 0 8px 24px rgba(0,0,0,0.15); }
        .card .icon { font-size: 3rem; margin-bottom: 14px; }
        .card h3 { font-size: 1.1rem; margin-bottom: 10px; color: #1976d2; }
        .card p  { font-size: 0.9rem; color: #666; margin-bottom: 18px; }
        .btn {
            display: inline-block; padding: 10px 22px; border-radius: 6px;
            text-decoration: none; font-weight: bold; font-size: 0.9rem;
            background: #1976d2; color: white; transition: background 0.2s;
        }
        .btn:hover { background: #1565c0; }
        .btn.green  { background: #388e3c; } .btn.green:hover  { background: #2e7d32; }
        .btn.orange { background: #f57c00; } .btn.orange:hover { background: #e65100; }

        .tech-bar {
            background: #e3f2fd; padding: 20px; text-align: center;
            font-size: 0.88rem; color: #555; border-top: 1px solid #bbdefb;
        }
        .tech-bar span { margin: 0 8px; font-weight: 600; color: #1976d2; }

        <%-- Check if user is logged in --%>
        <%
            String user = (session != null) ? (String) session.getAttribute("username") : null;
            boolean loggedIn = (user != null);
        %>
    </style>
</head>
<body>

<nav class="navbar">
    <h1>🎓 CollegeHub</h1>
    <div>
        <% if (loggedIn) { %>
            <span>Welcome, <strong><%= user %></strong></span>
            <a href="logout">Logout</a>
        <% } else { %>
            <a href="login.jsp">Login</a>
        <% } %>
    </div>
</nav>

<div class="hero">
    <h2>Distributed Student Management System</h2>
    <p>A full-stack demonstration combining Swing GUI, MVC, JDBC, Sockets, RMI, Servlets &amp; JSP</p>
</div>

<div class="cards">

    <div class="card">
        <div class="icon">📝</div>
        <h3>Register Student</h3>
        <p>Add a new student record to the system via the web interface.</p>
        <% if (loggedIn) { %>
            <a href="register.jsp" class="btn green">Register</a>
        <% } else { %>
            <a href="login.jsp" class="btn">Login to Register</a>
        <% } %>
    </div>

    <div class="card">
        <div class="icon">🔍</div>
        <h3>Search Students</h3>
        <p>Search by roll number or browse all registered students.</p>
        <% if (loggedIn) { %>
            <a href="search.jsp" class="btn orange">Search</a>
        <% } else { %>
            <a href="login.jsp" class="btn">Login to Search</a>
        <% } %>
    </div>

    <div class="card">
        <div class="icon">💻</div>
        <h3>Desktop Client</h3>
        <p>Launch the Swing-based desktop client for offline management.</p>
        <a href="#" class="btn" onclick="alert('Run Main.java in the desktop/ folder'); return false;">Learn More</a>
    </div>

</div>

<div class="tech-bar">
    Powered by:
    <span>Swing GUI</span>·<span>MVC Pattern</span>·<span>JDBC</span>·
    <span>TCP Sockets</span>·<span>Java RMI</span>·<span>Servlets</span>·<span>JSP</span>
</div>

</body>
</html>
