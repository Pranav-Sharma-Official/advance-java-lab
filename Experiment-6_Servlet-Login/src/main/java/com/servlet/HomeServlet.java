package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        HttpSession session =
                request.getSession(false);

        String username =
                (String) session.getAttribute("username");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Student Home</title></head>");
        out.println("<body>");

        out.println("<h1>Student Home Page</h1>");

        out.println("<h2>Welcome " + username + "</h2>");

        out.println("<p>You are successfully logged in.</p>");

        out.println("<a href='logout'>Logout</a>");

        out.println("</body>");
        out.println("</html>");
    }
}