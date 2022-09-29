package cn.utoverse.utoverselib.profile;

import cn.utoverse.utoverselib.UtoverseLibPlugin;
import ink.tuanzi.utoverselib.profile.UserProfile;
import ink.tuanzi.utoverselib.profile.UserProfileTimestamps;
import lombok.Getter;
import me.lucko.helper.Schedulers;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserProfileRepo implements TerminableModule {

    private static ConcurrentHashMap<String, UserProfile> userMap = new ConcurrentHashMap<>();
    @Getter
    private static List<String> usernameMap = new ArrayList<>();
    private final static String PROFILE_TIMESTAMPS_PATH = "timestamps";
    private final static String PROFILE_HOMES_PATH = "homes";

    @Override
    public void setup(@NotNull TerminableConsumer terminableConsumer) {
        loadUserNameMapAsync();
    }

    /**
     * 创建玩家档案
     *
     * @param userProfile 档案账号信息
     */
    public static void createProfile(UserProfile userProfile) {
        saveProfile(userProfile);
    }

    /**
     * 保存玩家档案
     *
     * @param userProfile 档案账号信息
     */
    public static void saveProfile(UserProfile userProfile) {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("uuid", userProfile.getUuid().toString());
        yaml.set("account-name", userProfile.getName());
        yaml.set("money", userProfile.getMoney().toPlainString());
        yaml.set("muted", userProfile.isMuted());
        yaml.set("jailed", userProfile.isJailed());
        yaml.set("ip-address", userProfile.getIpAddress());

        yaml.set(PROFILE_TIMESTAMPS_PATH + ".last-teleport", userProfile.getUserProfileTimestamps().getLastTeleport());
        yaml.set(PROFILE_TIMESTAMPS_PATH + ".mute", userProfile.getUserProfileTimestamps().getMute());
        yaml.set(PROFILE_TIMESTAMPS_PATH + ".jail", userProfile.getUserProfileTimestamps().getJail());
        yaml.set(PROFILE_TIMESTAMPS_PATH + ".logout", userProfile.getUserProfileTimestamps().getLogout());
        yaml.set(PROFILE_TIMESTAMPS_PATH + ".login", userProfile.getUserProfileTimestamps().getLogin());

        if (userProfile.getLogoutLocation() != null) {
            locationToYaml(yaml, "logout-location", userProfile.getLogoutLocation());
        }

        if (userProfile.getHomes() != null) {
            userProfile.getHomes().forEach((String name, Location loc) -> locationToYaml(yaml, PROFILE_HOMES_PATH + "." + name, loc));
        }

        try {
            yaml.save(getProfileRawData(userProfile.getName()));
            userMap.put(userProfile.getName(), userProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载档案
     *
     * @param name 玩家名
     * @return 档案账号信息
     */
    private static UserProfile loadProfile(String name) {
        File rawFile = getProfileRawData(name);
        if (!rawFile.exists()) {
            cn.utoverse.utoverselib.profile.UserProfile.create(Bukkit.getPlayerExact(name), new Date().getTime());
            return userMap.get(name);
        }

        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(getProfileRawData(name));

        UserProfileTimestamps userProfileTimestamps = UserProfileTimestamps.builder()
                .login(yaml.getLong(PROFILE_TIMESTAMPS_PATH + ".last-teleport", 0L))
                .jail(yaml.getLong(PROFILE_TIMESTAMPS_PATH + ".mute", 0L))
                .mute(yaml.getLong(PROFILE_TIMESTAMPS_PATH + ".jail", 0L))
                .lastTeleport(yaml.getLong(PROFILE_TIMESTAMPS_PATH + ".logout", 0L))
                .logout(yaml.getLong(PROFILE_TIMESTAMPS_PATH + ".login", 0L))
                .build();

        UserProfile userProfile = UserProfile.builder()
                .name(yaml.getString("account-name", name))
                .uuid(UUID.fromString(yaml.getString("uuid")))
                .money(BigDecimal.valueOf(yaml.getLong("money")))
                .muted(yaml.getBoolean("muted"))
                .jailed(yaml.getBoolean("jailed"))
                .ipAddress(yaml.getString("ip-address"))
                .userProfileTimestamps(userProfileTimestamps)
                .teleportSessions(new TreeMap<>())
                .build();

        ConfigurationSection homes = yaml.getConfigurationSection(PROFILE_HOMES_PATH);
        userProfile.setHomes(new HashMap<>());

        if (homes != null) {
            homes.getKeys(false).forEach(key -> {
                userProfile.getHomes().put(key, YamlToLocation(yaml, PROFILE_HOMES_PATH + "." + key));
            });
        }

        userMap.put(userProfile.getName(), userProfile);
        return userProfile;
    }


    /**
     * 获取玩家档案
     *
     * @param player 玩家
     * @return Account
     */
    public static <T extends OfflinePlayer> UserProfile getProfile(T player) {
        return getProfile(player.getName());
    }

    /**
     * 获取玩家档案
     *
     * @param name 玩家名
     * @return Account
     */
    public static UserProfile getProfile(String name) {
        if (userMap.get(name) != null) {
            return userMap.get(name);
        } else {
            return loadProfile(name);
        }
    }

    /**
     * 异步获取(刷新) 玩家名数据到缓存中
     */
    public static void loadUserNameMapAsync() {
        Schedulers.async().run(() -> {
            UtoverseLibPlugin plugin = UtoverseLibPlugin.getInstance();
            final File userdir = new File(getProfileDir());
            if (!userdir.exists()) {
                return;
            }
            usernameMap.clear();
            for (final String string : userdir.list()) {
                if (!string.endsWith(".yml")) {
                    continue;
                }
                usernameMap.add(string);
            }
        });
    }

    /**
     * 获取玩家数据元文件
     * 这可能不存在于磁盘中
     *
     * @param name 玩家名
     * @return File
     */
    private static File getProfileRawData(String name) {
        return new File(getProfileDir(), String.format("%s.yml", name));
    }

    /**
     * 坐标转 YAML
     *
     * @param yaml yaml对象
     * @param path 存储key路径
     * @param loc  坐标
     * @return yaml对象
     */
    private static YamlConfiguration locationToYaml(YamlConfiguration yaml, String path, Location loc) {
        yaml.set(path + ".x", loc.getX());
        yaml.set(path + ".y", loc.getY());
        yaml.set(path + ".z", loc.getZ());
        yaml.set(path + ".yaw", loc.getYaw());
        yaml.set(path + ".pitch", loc.getPitch());
        yaml.set(path + ".world", loc.getWorld().getUID().toString());
        yaml.set(path + ".world-name", loc.getWorld().getName());
        return yaml;
    }

    /**
     * YAML 转坐标
     *
     * @param yaml yaml对象
     * @param path 存储key路径
     * @return Location
     */
    private static Location YamlToLocation(YamlConfiguration yaml, String path) {
        double x = yaml.getDouble(path + ".x");
        double y = yaml.getDouble(path + ".y");
        double z = yaml.getDouble(path + ".z");
        float yaw = Float.parseFloat(yaml.getString(path + ".yaw"));
        float pitch = Float.parseFloat(yaml.getString(path + ".pitch"));
        World world = null;
        try {
            UUID uuid = UUID.fromString(yaml.getString(path + ".world"));
            world = Bukkit.getWorld(uuid);
        } catch (Exception e) {
            world = Bukkit.getWorld(yaml.getString(path + ".world-name"));
        }
        return new Location(world, x, y, z, yaw, pitch);
    }

    protected static String getProfileDir() {
        return UtoverseLibPlugin.getInstance().getDataFolder() + "/userdata";
    }
}
