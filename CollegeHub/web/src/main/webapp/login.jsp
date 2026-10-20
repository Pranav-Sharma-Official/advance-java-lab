<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login — CollegeHub</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Tahoma, sans-serif;
               background: linear-gradient(135deg, #1976d2, #42a5f5);
               min-height: 100vh; display: flex; align-items: center; justify-content: center; }
        .card {
            background: white; border-radius: 12px; padding: 40px 36px;
            width: 360px; box-shadow: 0 8px 32px rgba(0,0,0,0.2);
        }
        .card h2 { color: #1976d2; margin-bottom: 6px; font-size: 1.5rem; text-align: center; }
        .card p  { color: #888; font-size: 0.9rem; text-align: center; margin-bottom: 28px; }
        label    { font-size: 0.9rem; font-weight: 600; color: #444; display: block; margin-bottom: 6px; }
        input[type=text], input[type=password] {
            width: 100%; padding: 11px 14px; border: 1.5px solid #ccc; border-radius: 7px;
            font-size: 1rem; margin-bottom: 18px; transition: border 0.2s;
        }
        input:focus { outline: none; border-color: #1976d2; }
        .btn { width: 100%; padding: 13px; background: #1976d2; color: white;
               border: none; border-radius: 7px; font-size: 1rem; font-weight: bold;
               cursor: pointer; transition: background 0.2s; }
        .btn:hover { background: #1565c0; }
        .error { color: #d32f2f; background: #ffebee; border: 1px solid #ef9a9a;
                 border-radius: 6px; padding: 10px; font-size: 0.9rem; margin-bottom: 14px; }
        .back  { display: block; text-align: center; margin-top: 16px;
                 color: #1976d2; text-decoration: none; font-size: 0.9rem; }
        .hint  { font-size: 0.8rem; color: #aaa; text-align: center; margin-top: 18px; }
    </style>
</head>
<body>
<div class="card">
    <h2>🎓 CollegeHub</h2>
    <p>Sign in to access the student portal</p>

    <%
        String error = request.getParameter("error");
        if ("invalid".equals(error)) {
    %>
        <div class="error">❌ Invalid username or password. Please try again.</div>
    <% } %>

    <form action="login" method="post">
        <label for="username">Username</label>
        <input type="text" id="username" name="username" placeholder="Enter username" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="Enter password" required>

        <button type="submit" class="btn">Sign In</button>
    </form>

    <a class="back" href="index.jsp">← Back to Home</a>
    <p class="hint">Default credentials: admin / admin123</p>
</div>
</body>
</html>
