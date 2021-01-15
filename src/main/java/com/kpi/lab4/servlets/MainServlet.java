package com.kpi.lab4.servlets;

import com.kpi.lab4.exception.NotFoundException;
import com.kpi.lab4.servlets.actions.ActionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(MainServlet.class);
    private ActionFactory actionFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        actionFactory = new ActionFactory();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        try {
            actionFactory.getAction(uri).execute(req, resp);
        } catch(NotFoundException e) {
            logger.info("Action not found for path: " + uri);
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
        }
    }
}
