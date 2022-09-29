package cn.utoverse.utoverselib.api;

import cn.utoverse.utoverselib.UtoverseLibPlugin;
import ink.tuanzi.utoverselib.IUtoverseLib;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class UtoverseLibApiImpl implements IUtoverseLib {
    private UtoverseLibPlugin plugin;

    public UtoverseLibApiImpl(UtoverseLibPlugin plugin) {
        this.plugin = plugin;
    }

    public JavaPlugin getInstance() {
        return UtoverseLibPlugin.getInstance();
    }

    @Override
    public FileConfiguration getConfig() {
        return this.getConfig();
    }
}
