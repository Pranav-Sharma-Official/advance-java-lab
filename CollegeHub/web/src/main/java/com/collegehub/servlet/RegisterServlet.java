package com.collegehub.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.net.Socket;

/**
 * RegisterServlet — POST handler for student registration (Experiment 6 + 7).
 *
 * Flow:
 *   register.jsp (JSP form) ─POST→ RegisterServlet ─socket→ StudentServer
 *                                         │
 *                             forward to result.jsp (Experiment 7: JSP-Servlet forward)
 *
 * Communicates with the TCP StudentServer on localhost:6000.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String SERVER_HOST = "localhost";
    private static final int    SERVER_PORT = 6000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Forward GET requests back to the registration form
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String name       = sanitise(req.getParameter("name"));
        String rollNumber = sanitise(req.getParameter("rollNumber"));
        String branch     = sanitise(req.getParameter("branch"));
        String gender     = sanitise(req.getParameter("gender"));
        String gpaStr     = sanitise(req.getParameter("gpa"));

        // ── Validation ───────────────────────────────────────────────────
        if (name.isEmpty() || rollNumber.isEmpty() || branch.isEmpty() || gpaStr.isEmpty()) {
            req.setAttribute("errorMessage", "All fields are required.");
            req.setAttribute("name",       name);
            req.setAttribute("rollNumber", rollNumber);
            req.setAttribute("branch",     branch);
            req.setAttribute("gpa",        gpaStr);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        double gpa;
        try {
            gpa = Double.parseDouble(gpaStr);
            if (gpa < 0 || gpa > 10) throw new NumberFormatException("out of range");
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "GPA must be a number between 0.0 and 10.0.");
            req.setAttribute("name", name); req.setAttribute("rollNumber", rollNumber);
            req.setAttribute("branch", branch); req.setAttribute("gpa", gpaStr);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        // ── Talk to StudentServer via socket ─────────────────────────────
        String command  = "ADD|" + name + "|" + rollNumber + "|" + branch + "|" + gender + "|" + gpa;
        boolean success = false;
        String  message;

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            writer.println(command);
            String response = reader.readLine();

            if (response != null && response.startsWith("OK")) {
                success = true;
                message = "Student '" + name + "' registered successfully.";
            } else {
                message = response != null ? response.replace("ERROR|", "") : "Unknown server error.";
            }

        } catch (IOException e) {
            message = "Cannot connect to Student Server. Is it running on port " + SERVER_PORT + "? Error: " + e.getMessage();
        }

        // ── Forward to result.jsp (Experiment 7) ─────────────────────────
        req.setAttribute("success",    success);
        req.setAttribute("message",    message);
        req.setAttribute("name",       name);
        req.setAttribute("rollNumber", rollNumber);
        req.setAttribute("branch",     branch);
        req.setAttribute("gender",     gender);
        req.setAttribute("gpa",        String.valueOf(gpa));

        RequestDispatcher dispatcher = req.getRequestDispatcher("/result.jsp");
        dispatcher.forward(req, resp);
    }

    private String sanitise(String s) {
        return (s == null) ? "" : s.trim().replace("|", "-");
    }
}
