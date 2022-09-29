package ink.tuanzi.utoverselib.profile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

/**
 * 玩家档案
 */
@Builder
@ToString
@Getter
@Setter
public class UserProfile implements Cloneable {
    /**
     * 玩家 UUID
     */
    private UUID uuid;

    /**
     * 玩家名
     */
    private String name;

    /**
     * 基于 Vault 的货币系统数值
     */
    private BigDecimal money;

    /**
     * 家
     */
    private HashMap<String, Location> homes;

    /**
     * 是否被禁言
     */
    private boolean muted;

    /**
     * 是否被关在监狱
     */
    private boolean jailed;

    /**
     * 最近一次的登录 IP
     */
    private String ipAddress;

    /**
     * 登出时所在游戏内的位置
     */
    private Location logoutLocation;

    /**
     * 有关此玩家的时间类数据
     */
    private UserProfileTimestamps userProfileTimestamps;

    /**
     * 他人请求传送会话
     */
    private TreeMap<String, TeleportSession> teleportSessions;

    /**
     * 获取 Bukkit 玩家实例
     *
     * @return Bukkit 玩家
     */
    @Nullable
    public Player getPlayer() {
        return Bukkit.getPlayerExact(name);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
    }

}
