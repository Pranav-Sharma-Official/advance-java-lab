<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%
    // Guard — redirect to login if not authenticated (AuthFilter also guards /register)
    if (session.getAttribute("username") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register Student — CollegeHub</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Tahoma, sans-serif; background: #f0f4ff; }
        .navbar { background: #1976d2; color: white; padding: 14px 24px;
                  display: flex; justify-content: space-between; align-items: center; }
        .navbar h1 { font-size: 1.3rem; }
        .navbar a  { color: white; text-decoration: none; margin-left: 14px; }

        .container { max-width: 600px; margin: 40px auto; padding: 0 20px; }
        .card { background: white; border-radius: 12px; padding: 36px 40px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.1); }
        .card h2 { color: #1976d2; margin-bottom: 24px; font-size: 1.3rem; }

        .form-group { margin-bottom: 18px; }
        label { display: block; font-size: 0.9rem; font-weight: 600;
                color: #444; margin-bottom: 7px; }
        input[type=text], input[type=number], select {
            width: 100%; padding: 11px 14px; border: 1.5px solid #ccc;
            border-radius: 7px; font-size: 0.95rem; transition: border 0.2s;
        }
        input:focus, select:focus { outline: none; border-color: #1976d2; }

        .gender-group { display: flex; gap: 20px; align-items: center; }
        .gender-group label { font-weight: normal; display: flex; align-items: center; gap: 6px; cursor: pointer; }

        .btn-row { display: flex; gap: 12px; margin-top: 24px; }
        .btn { flex: 1; padding: 13px; border: none; border-radius: 7px;
               font-size: 1rem; font-weight: bold; cursor: pointer; transition: background 0.2s; }
        .btn-primary { background: #388e3c; color: white; }
        .btn-primary:hover { background: #2e7d32; }
        .btn-secondary { background: #e0e0e0; color: #333; }
        .btn-secondary:hover { background: #bdbdbd; }

        .error-msg { color: #d32f2f; background: #ffebee; border: 1px solid #ef9a9a;
                     border-radius: 6px; padding: 10px; font-size: 0.9rem; margin-bottom: 16px; }
    </style>
</head>
<body>

<nav class="navbar">
    <h1>🎓 CollegeHub</h1>
    <div>
        <a href="index.jsp">Home</a>
        <a href="search.jsp">Search</a>
        <a href="logout">Logout</a>
    </div>
</nav>

<div class="container">
    <div class="card">
        <h2>📋 Register New Student</h2>

        <%
            String errMsg = (String) request.getAttribute("errorMessage");
            if (errMsg != null) {
        %>
            <div class="error-msg">❌ <%= errMsg %></div>
        <% } %>

        <%-- JSP → Servlet forwarding (Experiment 7) --%>
        <form action="register" method="post">

            <div class="form-group">
                <label for="name">Full Name *</label>
                <input type="text" id="name" name="name" placeholder="e.g. Alice Sharma"
                       value="<%= request.getAttribute("name") != null ? request.getAttribute("name") : "" %>" required>
            </div>

            <div class="form-group">
                <label for="rollNumber">Roll Number *</label>
                <input type="text" id="rollNumber" name="rollNumber" placeholder="e.g. CS2024001"
                       value="<%= request.getAttribute("rollNumber") != null ? request.getAttribute("rollNumber") : "" %>" required>
            </div>

            <div class="form-group">
                <label for="branch">Branch *</label>
                <select id="branch" name="branch" required>
                    <option value="">-- Select Branch --</option>
                    <% String[] branches = {"Computer Science","Electronics","Mechanical","Civil","Information Technology","Chemical"};
                       String selBranch = request.getAttribute("branch") != null ? (String)request.getAttribute("branch") : "";
                       for (String b : branches) { %>
                        <option value="<%= b %>" <%= b.equals(selBranch) ? "selected" : "" %>><%= b %></option>
                    <% } %>
                </select>
            </div>

            <div class="form-group">
                <label>Gender *</label>
                <div class="gender-group">
                    <label><input type="radio" name="gender" value="Male" checked> Male</label>
                    <label><input type="radio" name="gender" value="Female"> Female</label>
                    <label><input type="radio" name="gender" value="Other"> Other</label>
                </div>
            </div>

            <div class="form-group">
                <label for="gpa">GPA (0.0 – 10.0) *</label>
                <input type="number" id="gpa" name="gpa" min="0" max="10" step="0.01"
                       placeholder="e.g. 8.75"
                       value="<%= request.getAttribute("gpa") != null ? request.getAttribute("gpa") : "" %>" required>
            </div>

            <div class="btn-row">
                <button type="submit" class="btn btn-primary">✅ Register Student</button>
                <button type="reset"  class="btn btn-secondary">🔄 Clear</button>
            </div>
        </form>
    </div>
</div>

</body>
</html>
