package com.kpi.lab4.services;

import com.kpi.lab4.dao.DaoFactory;

import java.sql.SQLException;

public class ServiceFactoryImpl extends ServiceFactory {

    @Override
    public UserService createUserService() throws SQLException {
        return new UserService(DaoFactory.createUserDao());
    }

    @Override
    public OrderService createOrderService() throws SQLException {
        return new OrderService(
                DaoFactory.createOrderDao(),
                DaoFactory.createRoomDao()
        );
    }

    @Override
    public RequestService createRequestService() throws SQLException {
        return new RequestService(
                DaoFactory.createRequestDao(),
                DaoFactory.createRoomDao(),
                DaoFactory.createOrderDao()
        );
    }

    @Override
    public SessionService createSessionService() throws SQLException {
        return new SessionService(DaoFactory.createSessionDao());
    }
}
