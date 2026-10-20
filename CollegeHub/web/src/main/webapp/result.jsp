<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    // This page receives data forwarded from RegisterServlet (Experiment 7 — forward pattern)
    Boolean success = (Boolean) request.getAttribute("success");
    String message  = (String)  request.getAttribute("message");
    String regName  = (String)  request.getAttribute("name");
    String regRoll  = (String)  request.getAttribute("rollNumber");
    String regBranch= (String)  request.getAttribute("branch");
    String regGender= (String)  request.getAttribute("gender");
    String regGpa   = (String)  request.getAttribute("gpa");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration Result — CollegeHub</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Tahoma, sans-serif; background: #f0f4ff; }
        .navbar { background: #1976d2; color: white; padding: 14px 24px;
                  display: flex; justify-content: space-between; align-items: center; }
        .navbar h1 { font-size: 1.3rem; }
        .navbar a  { color: white; text-decoration: none; margin-left: 14px; }
        .container { max-width: 560px; margin: 60px auto; padding: 0 20px; }
        .card { background: white; border-radius: 12px; padding: 40px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.1); text-align: center; }
        .icon { font-size: 4rem; margin-bottom: 16px; }
        h2 { margin-bottom: 10px; font-size: 1.4rem; }
        .msg { color: #666; margin-bottom: 28px; font-size: 0.97rem; }
        .details { background: #f5f5f5; border-radius: 8px; padding: 18px 22px;
                   text-align: left; margin-bottom: 28px; }
        .details p { font-size: 0.93rem; margin: 7px 0; color: #444; }
        .details p span { font-weight: bold; color: #1976d2; }
        .btn { display: inline-block; padding: 12px 26px; border-radius: 7px;
               text-decoration: none; font-weight: bold; font-size: 0.95rem;
               background: #1976d2; color: white; margin: 0 6px; transition: background 0.2s; }
        .btn:hover { background: #1565c0; }
        .btn.green { background: #388e3c; } .btn.green:hover { background: #2e7d32; }
        .success-color { color: #388e3c; }
        .error-color   { color: #d32f2f; }
    </style>
</head>
<body>

<nav class="navbar">
    <h1>🎓 CollegeHub</h1>
    <div>
        <a href="index.jsp">Home</a>
        <a href="register.jsp">Register</a>
        <a href="search.jsp">Search</a>
        <a href="logout">Logout</a>
    </div>
</nav>

<div class="container">
    <div class="card">
        <% if (Boolean.TRUE.equals(success)) { %>
            <div class="icon">✅</div>
            <h2 class="success-color">Registration Successful!</h2>
            <p class="msg"><%= message != null ? message : "The student has been registered." %></p>

            <div class="details">
                <p>Name:        <span><%= regName   %></span></p>
                <p>Roll Number: <span><%= regRoll   %></span></p>
                <p>Branch:      <span><%= regBranch %></span></p>
                <p>Gender:      <span><%= regGender %></span></p>
                <p>GPA:         <span><%= regGpa    %></span></p>
            </div>

            <a href="register.jsp" class="btn green">Register Another</a>
            <a href="search.jsp"   class="btn">View Students</a>

        <% } else { %>
            <div class="icon">❌</div>
            <h2 class="error-color">Registration Failed</h2>
            <p class="msg"><%= message != null ? message : "An unknown error occurred." %></p>
            <a href="register.jsp" class="btn">Try Again</a>
            <a href="index.jsp"    class="btn" style="background:#757575">Home</a>
        <% } %>
    </div>
</div>

</body>
</html>
