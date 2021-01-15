package com.kpi.lab4.dao;

import java.sql.Connection;

public class GeneralDao {
    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void releaseConnection() {
        ConnectionPool.releaseConnection(connection);
    }

    public Connection getConnection() {
        return connection;
    }
}
