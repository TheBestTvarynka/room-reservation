package com.kpi.lab4.dao;

import java.sql.SQLException;

public class DaoFactory {
    public static OrderDao createOrderDao() throws SQLException {
        return new OrderDao(ConnectionPool.getConnection());
    }

    public static UserDao createUserDao() throws SQLException {
        return new UserDao(ConnectionPool.getConnection());
    }

    public static RequestDao createRequestDao() throws SQLException {
        return new RequestDao(ConnectionPool.getConnection());
    }

    public static RoomDao createRoomDao() throws SQLException {
        return new RoomDao(ConnectionPool.getConnection());
    }

    public static SessionDao createSessionDao() throws SQLException {
        return new SessionDao(ConnectionPool.getConnection());
    }
}
