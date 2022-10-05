package ink.tuanzi.utoverselib;

import ink.tuanzi.utoverselib.util.ITeleport;
import org.bukkit.configuration.file.FileConfiguration;

public interface IUtoverseLib {

    /**
     * 获取插件配置文件
     *
     * @return
     */
    FileConfiguration getConfig();

    /**
     * 获取传送工具类
     *
     * @return
     */
    ITeleport getTeleportUtil();
}