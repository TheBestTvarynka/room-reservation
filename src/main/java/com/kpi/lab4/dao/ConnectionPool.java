package com.kpi.lab4.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static volatile DataSource dataSource;

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(DataSource ds) {
        dataSource = ds;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
