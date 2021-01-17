package com.kpi.lab4.servlets.config;

import com.kpi.lab4.dao.ConnectionPool;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener("Creates a connection pool")
public class ConnectionPoolContextListener implements ServletContextListener {
    private static Logger logger = LogManager.getLogger(ConnectionPoolContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (ConnectionPool.getDataSource() == null) {
            String dbName = System.getenv("DB_NAME");
            String cloudSqlInstance = System.getenv("CLOUD_SQL_INSTANCE");
            String username = System.getenv("DB_USERNAME");
            String password = System.getenv("DB_PASSWORD");
            logger.debug("Database config:");
            logger.debug(dbName);
            logger.debug(cloudSqlInstance);
            logger.debug(username);
            logger.debug(password);

            HikariConfig config = new HikariConfig();
            config.setDriverClassName("org.postgresql.Driver");
            config.setJdbcUrl(String.format("jdbc:postgresql:///%s", dbName));
            config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
            config.addDataSourceProperty("cloudSqlInstance", cloudSqlInstance);
            config.addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE");
            config.setUsername(username);
            config.setPassword(password);
            config.addDataSourceProperty("cachePrepStmts" , "true");
            config.addDataSourceProperty("prepStmtCacheSize" , "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit" , "2048");
            config.setConnectionTimeout(20000);
            config.setMaximumPoolSize(50);
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
