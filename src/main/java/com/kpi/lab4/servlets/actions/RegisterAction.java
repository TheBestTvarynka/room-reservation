package com.kpi.lab4.servlets.actions;

import com.kpi.lab4.dto.RegisterDto;
import com.kpi.lab4.exception.UnavailableException;
import com.kpi.lab4.exception.UserAlreadyExistException;
import com.kpi.lab4.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterAction implements Action {
    private UserService userService;

    public RegisterAction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
    }

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            userService.register(new RegisterDto(
                    request.getParameter("username"),
                    request.getParameter("email"),
                    request.getParameter("fullName"),
                    request.getParameter("password")
            ));
            request.setAttribute("message", "Register success. You can login now.");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        } catch (UserAlreadyExistException | UnavailableException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
        }
    }
}
