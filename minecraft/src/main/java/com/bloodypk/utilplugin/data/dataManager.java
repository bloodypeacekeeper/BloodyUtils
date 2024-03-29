package com.bloodypk.utilplugin.data;

import com.bloodypk.utilplugin.UtilPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class dataManager {

    private UtilPlugin main;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public dataManager(UtilPlugin main) {
        this.main = main;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (configFile == null) {
            configFile = new File(main.getDataFolder(), "data.yml");
        }

        dataConfig = YamlConfiguration.loadConfiguration(configFile);

        InputStream defaultStream = main.getResource("data.yml");

        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (dataConfig == null)
            reloadConfig();

        return this.dataConfig;
    }

    public void saveConfig() {
        if (dataConfig == null || configFile == null)
            return;

        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            main.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(main.getDataFolder(), "data.yml");
        }

        if (!configFile.exists()) {
            main.saveResource("data.yml", false);
        }
    }
}
