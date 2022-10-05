package cn.utoverse.utoverselib.api;

import cn.utoverse.utoverselib.UtoverseLibPlugin;
import cn.utoverse.utoverselib.api.util.ApiTeleportUtil;
import ink.tuanzi.utoverselib.IUtoverseLib;
import ink.tuanzi.utoverselib.util.ITeleport;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class UtoverseLibApiImpl implements IUtoverseLib {
    private UtoverseLibPlugin plugin;

    public UtoverseLibApiImpl(UtoverseLibPlugin plugin) {
        this.plugin = plugin;
    }

    private JavaPlugin getInstance() {
        return UtoverseLibPlugin.getInstance();
    }

    @Override
    public FileConfiguration getConfig() {
        return this.getConfig();
    }

    @Override
    public ITeleport getTeleportUtil() {
        return new ApiTeleportUtil();
    }
}
