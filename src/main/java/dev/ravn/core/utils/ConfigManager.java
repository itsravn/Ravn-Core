package dev.ravn.core.utils;

import dev.ravn.core.RavnCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Manages creation, loading, and saving of YAML configurations.
 */
public class ConfigManager {

    private final RavnCore plugin;
    private FileConfiguration configuration;
    private File file;
    private final String fileName;

    public ConfigManager(RavnCore plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        saveDefaultConfig();
    }

    public void saveDefaultConfig() {
        if (file == null) {
            file = new File(plugin.getDataFolder(), fileName);
        }
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
    }

    public FileConfiguration getConfig() {
        if (configuration == null) {
            reloadConfig();
        }
        return configuration;
    }

    public void reloadConfig() {
        if (file == null) {
            file = new File(plugin.getDataFolder(), fileName);
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig() {
        if (configuration == null || file == null) {
            return;
        }
        try {
            configuration.save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + file, e);
        }
    }
}
