package com.kpi.lab4.servlets.actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Action {
    default void get(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(
                "error",
                "Method GET not allowed for path " + request.getServletPath()
        );
        request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
    }

    default void post(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(
                "error",
                "Method POST not allowed for path " + request.getServletPath()
        );
        request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
    }

    default void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getMethod();
        switch (method) {
            case "GET":
                this.get(request, response);
                break;
            case "POST":
                this.post(request, response);
                break;
            default:
                request.setAttribute(
                        "error",
                        "Method " + method + " not allowed for path " + request.getServletPath()
                );
                request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
    }
}
