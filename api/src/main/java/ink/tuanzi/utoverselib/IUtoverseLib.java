package ink.tuanzi.utoverselib;

import org.bukkit.configuration.file.FileConfiguration;

public interface IUtoverseLib {
    /**
     * 获取插件配置文件
     *
     * @return
     */
    FileConfiguration getConfig();
}