package com.collegehub.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * AuthFilter — Protects secured routes (Experiment 6: Filter + Session Management).
 *
 * If the user does not have a valid session, they are redirected to /login.jsp.
 * Public paths (/login, /login.jsp, /index.jsp, static assets) bypass this filter.
 */
@WebFilter(urlPatterns = {"/register", "/search", "/result.jsp"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("[AuthFilter] Initialised — protecting /register, /search, /result.jsp");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  httpReq  = (HttpServletRequest)  request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        HttpSession session  = httpReq.getSession(false);
        boolean     loggedIn = (session != null && session.getAttribute("username") != null);

        if (loggedIn) {
            // ── Authenticated: proceed down the filter chain ─────────────
            System.out.println("[AuthFilter] ALLOW " + session.getAttribute("username")
                    + " → " + httpReq.getRequestURI());
            chain.doFilter(request, response);

        } else {
            // ── Not authenticated: redirect to login ─────────────────────
            System.out.println("[AuthFilter] BLOCK (no session) → " + httpReq.getRequestURI());
            String loginUrl = httpReq.getContextPath() + "/login.jsp";
            httpResp.sendRedirect(loginUrl);
        }
    }

    @Override
    public void destroy() {
        System.out.println("[AuthFilter] Destroyed.");
    }
}
