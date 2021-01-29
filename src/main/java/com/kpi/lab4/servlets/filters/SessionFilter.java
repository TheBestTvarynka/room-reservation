package com.kpi.lab4.servlets.filters;

import com.kpi.lab4.entities.Session;
import com.kpi.lab4.services.ServiceFactory;
import com.kpi.lab4.services.SessionService;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class SessionFilter implements Filter {
    private static Logger logger = LogManager.getLogger(SessionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            logger.info("User already logged in: " + session.getAttribute("username"));
            chain.doFilter(req, res);
        } else if (session == null) {
            logger.debug("Try to find sessionId in cookies...");
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie: cookies) {
                    if (cookie.getName().equals("JSESSIONID")) {
                        logger.debug("Try to find session in database... " + cookie.getValue());
                        checkDatabaseSession(cookie.getValue(), req, res, chain);
                        return;
                    }
                }
            } else {
                logger.error("Cookies is equal to null...");
            }
            logger.error("Redirect to login...");
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            System.out.println(session);
            logger.error("Redirect to login...");
            res.sendRedirect(req.getContextPath() + "/login");
        }
    }

    private void checkDatabaseSession(String sessionId, HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, SQLException, ServletException {
        SessionService sessionService = ServiceFactory.getInstance().createSessionService();
        Optional<Session> session = sessionService.findById(sessionId);
        if (session.isPresent()) {
            logger.debug("Found session in database...");
            Session sessionData = session.get();
            if (LocalDateTime.now().compareTo(sessionData.getValidUntil()) >= 0) {
                logger.error(String.format("Session %s expired. Redirect to login...", sessionData.getSessionId()));
                sessionService.delete(sessionData.getSessionId());
                res.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            HttpSession httpSession = req.getSession(true);
            httpSession.setAttribute("user_id", sessionData.getUserId());
            httpSession.setAttribute("username", sessionData.getUsername());
            httpSession.setAttribute("role", sessionData.getRole());
            // 60 * 60 = 3_600 = 1 hour
            httpSession.setMaxInactiveInterval(3_600);

            LocalDateTime validUntil = LocalDateTime.now();
            validUntil = validUntil.plusHours(1);
            sessionData.setValidUntil(validUntil);
            sessionService.update(sessionData);
            chain.doFilter(req, res);
        } else {
            logger.error("Session not found. Redirect to login...");
            res.sendRedirect(req.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() { }
}
