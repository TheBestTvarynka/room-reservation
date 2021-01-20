package com.kpi.lab4.servlets.actions;

import com.kpi.lab4.dto.LoginDto;
import com.kpi.lab4.dto.SessionDto;
import com.kpi.lab4.entities.User;
import com.kpi.lab4.exception.UnavailableException;
import com.kpi.lab4.services.SessionService;
import com.kpi.lab4.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginAction implements Action {
    private final UserService userService;
    private final SessionService sessionService;

    public LoginAction(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            User user = userService.login(new LoginDto(
                    request.getParameter("username"),
                    request.getParameter("password")
            ));
            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user_id", user.getId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getUserType());
                // 60 * 60 = 3_600 = 1 hour
                session.setMaxInactiveInterval(3_600);
                sessionService.saveSession(new SessionDto(
                        session.getId(),
                        user.getId(),
                        user.getUserType(),
                        user.getUsername()
                ));
                response.sendRedirect(request.getContextPath() + "/request");
            } else {
                request.setAttribute("error", "Incorrect username or password.");
                request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            }
        } catch (UnavailableException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        } catch (IndexOutOfBoundsException e) {
            request.setAttribute("error", "Incorrect username or password.");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
        userService.finish();
        sessionService.finish();
    }
}
