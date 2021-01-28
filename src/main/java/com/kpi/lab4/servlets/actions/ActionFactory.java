package com.kpi.lab4.servlets.actions;

import com.kpi.lab4.exception.NotFoundException;
import com.kpi.lab4.services.ServiceFactory;
import com.kpi.lab4.servlets.security.CsrfTokenGenerator;

import java.sql.SQLException;

public class ActionFactory {
    public Action getAction(String path) throws SQLException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        switch (path) {
            case "/home":
                return new HomeAction();
            case "/register":
                return new RegisterAction(serviceFactory.createUserService());
            case "/login":
                return new LoginAction(
                        serviceFactory.createUserService(),
                        serviceFactory.createSessionService(),
                        new CsrfTokenGenerator()
                );
            case "/browse":
                return new BrowseAction(serviceFactory.createRequestService());
            case "/request":
                return new RequestAction(serviceFactory.createRequestService());
            case "/order":
                return new OrderAction(serviceFactory.createOrderService());
            case "/logout":
                return new LogOutAction();
            case "/resolve":
                return new ResolveRequestAction();
            default:
                throw new NotFoundException("Request you are looking for not found");
        }
    }
}
