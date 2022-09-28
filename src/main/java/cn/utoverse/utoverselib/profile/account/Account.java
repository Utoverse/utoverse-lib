package cn.utoverse.utoverselib.profile.account;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Builder
public class Account implements Cloneable {
    /**
     * 玩家 UUID
     */
    @Getter
    @Setter
    private UUID uuid;

    /**
     * 玩家名
     */
    @Getter
    @Setter
    private String name;

    /**
     * 基于 Vault 的货币系统数值
     */
    @Getter
    @Setter
    private BigDecimal money;

    /**
     * 家
     */
    @Getter
    @Setter
    private ConcurrentHashMap<String, Location> homes;

    /**
     * 是否被禁言
     */
    @Getter
    @Setter
    private boolean muted;

    /**
     * 是否被关在监狱
     */
    @Getter
    @Setter
    private boolean jailed;

    /**
     * 最近一次的登录 IP
     */
    @Getter
    @Setter
    private String ipAddress;

    /**
     * 登出时所在游戏内的位置
     */
    @Getter
    @Setter
    private Location logoutLocation;

    /**
     * 有关此玩家的时间类数据
     */
    @Getter
    @Setter
    private AccountTimes accountTimes;

    /**
     * 他人请求传送会话
     */
    @Getter
    @Setter
    private TreeMap<String, TeleportSession> teleportSessions;

    /**
     * 获取 Bukkit 玩家实例
     * @return
     */
    public Player getPlayer() {
        return Bukkit.getPlayerExact(name);
    }

    public Account clone() {
        try {
            return (Account) super.clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
    }
}
