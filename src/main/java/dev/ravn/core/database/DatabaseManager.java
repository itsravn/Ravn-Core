package dev.ravn.core.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Singleton DatabaseManager handling Async HikariCP connection pooling.
 */
public class DatabaseManager {

    private static DatabaseManager instance;
    private HikariDataSource dataSource;
    private final Logger logger = Logger.getLogger("DatabaseManager");

    private DatabaseManager() {
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Initialize the database connection pool.
     *
     * @param host     Database host
     * @param port     Database port
     * @param database Database name
     * @param user     Database user
     * @param password Database password
     */
    public void initialize(String host, int port, String database, String user, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setUsername(user);
        config.setPassword(password);

        // Performance properties
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");

        // Pool settings
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(5000);
        config.setPoolName("Ravn-HikariPool");

        this.dataSource = new HikariDataSource(config);
        logger.info("Database connection pool initialized.");
    }

    /**
     * Get a connection from the pool.
     * 
     * @return Connection
     * @throws SQLException if pool is exhausted or initialization failed
     */
    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource is not initialized!");
        }
        return dataSource.getConnection();
    }

    /**
     * Close the connection pool.
     */
    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            logger.info("Database connection pool closed.");
        }
    }

    /**
     * Execute a runnable task asynchronously.
     * In a real implementation, this would likely use the plugin's scheduler.
     * Here, using CompletableFuture for generic async capability.
     */
    public CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable);
    }
}
