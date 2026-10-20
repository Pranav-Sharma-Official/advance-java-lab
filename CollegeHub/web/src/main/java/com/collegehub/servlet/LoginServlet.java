package com.collegehub.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * LoginServlet — Session Management (Experiment 6).
 *
 * POST /login  : validates credentials, creates session, redirects home.
 * GET  /login  : redirects to login.jsp
 *
 * Hard-coded credentials for demo (in production use DB + hashed passwords):
 *   admin / admin123
 *   student / student456
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Demo credential store (username → password)
    private static final java.util.Map<String, String> USERS = java.util.Map.of(
            "admin",   "admin123",
            "student", "student456"
    );

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // If already logged in, go home
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null ||
                username.isBlank() || password.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=invalid");
            return;
        }

        username = username.trim();

        String expectedPwd = USERS.get(username);

        if (expectedPwd != null && expectedPwd.equals(password)) {
            // ── Valid credentials ──────────────────────────────────────────
            // Invalidate any old session first (session fixation prevention)
            HttpSession oldSession = req.getSession(false);
            if (oldSession != null) oldSession.invalidate();

            HttpSession session = req.getSession(true);
            session.setAttribute("username", username);
            session.setAttribute("role", username.equals("admin") ? "ADMIN" : "STUDENT");
            session.setMaxInactiveInterval(30 * 60); // 30 minutes

            System.out.println("[Login] User logged in: " + username);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");

        } else {
            // ── Invalid credentials ────────────────────────────────────────
            System.out.println("[Login] Failed login attempt for: " + username);
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=invalid");
        }
    }
}
