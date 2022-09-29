package ink.tuanzi.utoverselib.profile;

import org.bukkit.OfflinePlayer;

import java.util.List;

/**
 * 玩家档案工具类
 */
public interface UserProfileRepo {

    /**
     * 获取玩家档案
     *
     * @param player 玩家
     * @param <T>    继承自 OfflinePlayer 的玩家实例
     * @return 玩家档案
     */
    <T extends OfflinePlayer> UserProfile getProfile(T player);

    /**
     * 获取玩家档案
     *
     * @param playerName 玩家名
     * @return 玩家档案
     */
    UserProfile getProfile(String playerName);

    /**
     * 刷新玩家名缓存数据
     */
    void loadUserNameMapAsync();

    /**
     * 获取玩家名列表
     *
     * @return 玩家名列表
     */
    List<String> getUserNameMap();

}
