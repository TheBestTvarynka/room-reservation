package com.kpi.lab4.services;

import java.sql.SQLException;

public abstract class ServiceFactory {
    private volatile static ServiceFactory serviceFactory;

    public abstract UserService createUserService() throws SQLException;
    public abstract OrderService createOrderService() throws SQLException;
    public abstract RequestService createRequestService() throws SQLException;
    public abstract SessionService createSessionService() throws SQLException;

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            synchronized (ServiceFactory.class) {
                if (serviceFactory == null) {
                    serviceFactory = new ServiceFactoryImpl();
                }
            }
        }
        return serviceFactory;
    }
}
