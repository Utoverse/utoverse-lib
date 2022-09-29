package cn.utoverse.utoverselib.util.config;

import cn.utoverse.utoverselib.UtoverseLibPlugin;
import com.tchristofferson.configupdater.ConfigUpdater;
import ink.tuanzi.utoverselib.constant.ConfigFile;
import ink.tuanzi.utoverselib.util.IConfiguration;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class Configuration implements IConfiguration {

    @Getter
    private static ConcurrentHashMap<ConfigFile, YamlConfiguration> files = new ConcurrentHashMap<>();

    /**
     * 载入配置文件
     */
    public static void init() {
        UtoverseLibPlugin plugin = UtoverseLibPlugin.getInstance();

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
        reloadAllConfig();
    }

    /**
     * 重载配置文件
     */
    public static void reloadAllConfig() {
        UtoverseLibPlugin plugin = UtoverseLibPlugin.getInstance();
        files.put(ConfigFile.CONFIG, YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/" + ConfigFile.CONFIG.getPath())));
    }

    @Override
    public void reload() {
        reloadAllConfig();
    }
}
