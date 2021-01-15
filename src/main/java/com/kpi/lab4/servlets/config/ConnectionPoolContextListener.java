package com.kpi.lab4.servlets.config;

import com.kpi.lab4.dao.ConnectionPool;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener("Creates a connection pool")
public class ConnectionPoolContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (ConnectionPool.getDataSource() == null) {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName("org.postgresql.Driver");
            // ?
            config.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/java_lab");
            // --
            config.setUsername("postgres");
            config.setPassword("postgres");
            config.addDataSourceProperty("cachePrepStmts" , "true");
            config.addDataSourceProperty("prepStmtCacheSize" , "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit" , "2048");
            DataSource dataSource = new HikariDataSource(config);
            ConnectionPool.setDataSource(dataSource);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (ConnectionPool.getDataSource() != null) {
            HikariDataSource dataSource = (HikariDataSource) ConnectionPool.getDataSource();
            dataSource.close();
        }
    }

}
