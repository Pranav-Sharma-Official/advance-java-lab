package com.collegehub.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * SearchServlet — Handles student search requests (GET).
 * Connects to StudentServer via socket, retrieves data, forwards to search.jsp.
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private static final String SERVER_HOST = "localhost";
    private static final int    SERVER_PORT = 6000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String roll = req.getParameter("roll");
        List<String[]> students = new ArrayList<>();
        String infoMessage  = null;
        String errorMessage = null;

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            if (roll != null && !roll.isBlank()) {
                // ── SEARCH by roll number ────────────────────────────────
                writer.println("SEARCH|" + roll.trim());
                String response = reader.readLine();

                if (response != null && !response.startsWith("NOT_FOUND") && !response.startsWith("ERROR")) {
                    String[] parts = response.split("\\|", -1);
                    if (parts.length >= 6) {
                        students.add(parts);
                        infoMessage = "Found 1 student with roll number: " + roll.trim();
                    }
                } else {
                    infoMessage = "No student found with roll number: " + roll.trim();
                }

            } else {
                // ── LIST all ─────────────────────────────────────────────
                writer.println("LIST");
                String line;
                while ((line = reader.readLine()) != null && !line.equals("END")) {
                    if (!line.startsWith("ERROR")) {
                        String[] parts = line.split("\\|", -1);
                        if (parts.length >= 6) students.add(parts);
                    }
                }
                infoMessage = "Showing all " + students.size() + " student(s).";
            }

        } catch (IOException e) {
            errorMessage = "Cannot connect to Student Server (port " + SERVER_PORT + "): " + e.getMessage();
        }

        req.setAttribute("students",     students);
        req.setAttribute("infoMessage",  infoMessage);
        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher("/search.jsp").forward(req, resp);
    }
}
