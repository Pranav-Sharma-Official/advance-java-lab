<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Search Students — CollegeHub</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Tahoma, sans-serif; background: #f0f4ff; }
        .navbar { background: #1976d2; color: white; padding: 14px 24px;
                  display: flex; justify-content: space-between; align-items: center; }
        .navbar h1 { font-size: 1.3rem; }
        .navbar a  { color: white; text-decoration: none; margin-left: 14px; }

        .container { max-width: 900px; margin: 40px auto; padding: 0 20px; }
        .card { background: white; border-radius: 12px; padding: 36px 40px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.1); }
        .card h2 { color: #1976d2; margin-bottom: 24px; font-size: 1.3rem; }

        .search-bar { display: flex; gap: 12px; margin-bottom: 28px; }
        .search-bar input { flex: 1; padding: 11px 14px; border: 1.5px solid #ccc;
                             border-radius: 7px; font-size: 0.95rem; }
        .search-bar input:focus { outline: none; border-color: #1976d2; }
        .btn { padding: 11px 22px; border: none; border-radius: 7px; font-size: 0.95rem;
               font-weight: bold; cursor: pointer; color: white; transition: background 0.2s; }
        .btn-blue   { background: #1976d2; } .btn-blue:hover   { background: #1565c0; }
        .btn-teal   { background: #00897b; } .btn-teal:hover   { background: #00695c; }

        table { width: 100%; border-collapse: collapse; }
        th { background: #1976d2; color: white; padding: 12px 14px; text-align: left; font-size: 0.92rem; }
        td { padding: 11px 14px; border-bottom: 1px solid #eee; font-size: 0.92rem; }
        tr:hover td { background: #e3f2fd; }
        .empty { text-align: center; color: #888; padding: 30px; font-style: italic; }

        .info { color: #1976d2; background: #e3f2fd; border: 1px solid #90caf9;
                border-radius: 6px; padding: 10px 14px; margin-bottom: 16px; font-size: 0.9rem; }
        .error-msg { color: #d32f2f; background: #ffebee; border: 1px solid #ef9a9a;
                     border-radius: 6px; padding: 10px; font-size: 0.9rem; margin-bottom: 16px; }
        .grade-badge {
            display: inline-block; padding: 3px 10px; border-radius: 12px;
            font-weight: bold; font-size: 0.85rem; background: #e8f5e9; color: #2e7d32;
        }
    </style>
</head>
<body>

<nav class="navbar">
    <h1>🎓 CollegeHub</h1>
    <div>
        <a href="index.jsp">Home</a>
        <a href="register.jsp">Register</a>
        <a href="logout">Logout</a>
    </div>
</nav>

<div class="container">
    <div class="card">
        <h2>🔍 Search Students</h2>

        <form action="search" method="get">
            <div class="search-bar">
                <input type="text" name="roll" placeholder="Enter Roll Number (leave empty to list all)"
                       value="<%= request.getParameter("roll") != null ? request.getParameter("roll") : "" %>">
                <button type="submit" class="btn btn-blue">Search</button>
                <button type="submit" name="roll" value="" class="btn btn-teal">List All</button>
            </div>
        </form>

        <%
            String infoMsg  = (String) request.getAttribute("infoMessage");
            String errMsg   = (String) request.getAttribute("errorMessage");
            @SuppressWarnings("unchecked")
            List<String[]> students = (List<String[]>) request.getAttribute("students");

            if (infoMsg != null) { %><div class="info">ℹ️ <%= infoMsg %></div><% }
            if (errMsg  != null) { %><div class="error-msg">❌ <%= errMsg %></div><% }
        %>

        <% if (students != null) { %>
        <table>
            <thead>
                <tr>
                    <th>ID</th><th>Name</th><th>Roll Number</th>
                    <th>Branch</th><th>Gender</th><th>GPA</th><th>Grade</th>
                </tr>
            </thead>
            <tbody>
            <% if (students.isEmpty()) { %>
                <tr><td colspan="7" class="empty">No students found.</td></tr>
            <% } else {
                for (String[] s : students) {
                    double gpa = 0;
                    try { gpa = Double.parseDouble(s[5]); } catch (Exception ignored) {}
                    String grade;
                    if      (gpa >= 9.0) grade = "O";
                    else if (gpa >= 8.5) grade = "A+";
                    else if (gpa >= 8.0) grade = "A";
                    else if (gpa >= 7.0) grade = "B+";
                    else if (gpa >= 6.0) grade = "B";
                    else if (gpa >= 5.0) grade = "C";
                    else                 grade = "F";
            %>
                <tr>
                    <td><%= s[0] %></td>
                    <td><strong><%= s[1] %></strong></td>
                    <td><%= s[2] %></td>
                    <td><%= s[3] %></td>
                    <td><%= s[4] %></td>
                    <td><%= s[5] %></td>
                    <td><span class="grade-badge"><%= grade %></span></td>
                </tr>
            <% } } %>
            </tbody>
        </table>
        <% } %>
    </div>
</div>

</body>
</html>
