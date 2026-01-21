package dev.ravn.core;

import dev.ravn.core.database.DatabaseManager;
import dev.ravn.core.redis.RedisManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Ravn-Core: High-performance backbone for next-gen networks.
 *
 * @author RavnTeam
 * @version 1.0.0
 */
public class RavnCore extends JavaPlugin {

    private static RavnCore instance;
    private DatabaseManager databaseManager;
    private RedisManager redisManager;

    @Override
    public void onEnable() {
        instance = this;
        long startTime = System.currentTimeMillis();

        getLogger().info("Ravn-Core is initializing...");

        // Initialize Managers
        initializeManagers();

        getLogger().info("Ravn-Core enabled in " + (System.currentTimeMillis() - startTime) + "ms.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Ravn-Core is shutting down...");

        if (databaseManager != null) {
            databaseManager.closePool();
        }
        if (redisManager != null) {
            redisManager.closePool();
        }

        getLogger().info("Ravn-Core disabled.");
    }

    private void initializeManagers() {
        try {
            // Placeholder initialization - In a real scenario, we'd load config credentials here
            // databaseManager = DatabaseManager.getInstance(); 
            // databaseManager.initialize("host", 3306, "db", "user", "pass");
            
            // redisManager = RedisManager.getInstance();
            // redisManager.initialize("localhost", 6379, null);

            getLogger().info("Subsystems initialized.");
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to initialize subsystems!", e);
        }
    }

    public static RavnCore getInstance() {
        return instance;
    }
    
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }
}
