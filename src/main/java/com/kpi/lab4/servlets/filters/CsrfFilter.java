package com.kpi.lab4.servlets.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

public class CsrfFilter implements Filter {
    private static Logger logger = LogManager.getLogger(CsrfFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String method = req.getMethod();
        if (method.equals("POST")) {
            HttpSession session = req.getSession(false);
            String[] csrfTokenValues = req.getParameterValues("csrf-token");
            if (csrfTokenValues == null || csrfTokenValues.length != 1) {
                logger.error("csrfTokenValues: " + Arrays.toString(csrfTokenValues));
                fail(req, resp);
                return;
            }
            String csrfRequestToken = csrfTokenValues[0];
            String csrfSessionToken = (String) session.getAttribute("csrf-token");
            logger.debug("csrfRequestToken: " + csrfRequestToken);
            logger.debug("csrfSessionToken: " + csrfSessionToken);
            if (!csrfRequestToken.equals(csrfSessionToken)) {
                fail(req, resp);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private void fail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession(false).invalidate();
        response.sendRedirect("/login");
    }

    @Override
    public void destroy() { }
}
