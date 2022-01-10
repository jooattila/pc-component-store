package edu.bbte.idde.jaim1826.backend.dao.jdbc;

import edu.bbte.idde.jaim1826.backend.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public final class ConnectionPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);
    private Integer poolSize;
    private static ConnectionPool instance;
    private final Stack<Connection> pool = new Stack<>();

    private ConnectionPool() {
        try {
            poolSize = Integer.parseInt(ConfigFactory.getConfig().getConnectionPoolSize());
            Class.forName(ConfigFactory.getConfig().getDatabaseDriver());
            for (int i = 0; i < poolSize; ++i) {
                pool.push(DriverManager.getConnection(ConfigFactory.getConfig().getDatabaseURL(),
                        ConfigFactory.getConfig().getDatabaseUsername(),
                        ConfigFactory.getConfig().getDatabasePassword()));
            }
            LOGGER.info("Initialized pool of size {}", poolSize);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("Connection could not be established", e);
        }
    }

    public synchronized Connection getConnection() {
        if (pool.isEmpty()) {
            LOGGER.error("No connections in pool");
        }
        LOGGER.info("Giving out connection from pool");
        return pool.pop();
    }

    public synchronized void returnConnection(Connection connection) {
        if (pool.size() < poolSize) {
            LOGGER.info("Returning connection to pool");
            pool.push(connection);
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }
}
