package cn.utoverse.utoverselib.util.config;

import cn.utoverse.utoverselib.AbstractUtoverseLibPlugin;
import com.tchristofferson.configupdater.ConfigUpdater;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class Configuration {

    @Getter
    private static ConcurrentHashMap<ConfigFile, YamlConfiguration> files = new ConcurrentHashMap<>();

    /**
     * 载入配置文件
     */
    public static void init() {
        AbstractUtoverseLibPlugin plugin = AbstractUtoverseLibPlugin.getInstance();

        List<String> defaultFiles = Arrays.asList(ConfigFile.CONFIG.getPath());
        for (String name : defaultFiles) {
            File file = new File(plugin.getDataFolder(), name);
            if (!file.exists()) {
                plugin.getDataFolder().mkdirs();
                plugin.saveResource(name, false);
                plugin.getLogger().info("Created config file " + name);
            }
        }
        try {
            ConfigUpdater.update(plugin, ConfigFile.CONFIG.getPath(), new File(plugin.getDataFolder(), ConfigFile.CONFIG.getPath()));
        } catch (IOException ex) {
            plugin.getLogger().log(Level.WARNING, "Error while trying to update a config file delete all config files and restart the server", ex);
        }
        reload();
    }

    /**
     * 重载配置文件
     */
    public static void reload() {
        AbstractUtoverseLibPlugin plugin = AbstractUtoverseLibPlugin.getInstance();
        files.put(ConfigFile.CONFIG, YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/" + ConfigFile.CONFIG.getPath())));
    }
}
