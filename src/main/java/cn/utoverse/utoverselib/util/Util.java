package cn.utoverse.utoverselib.util;

import cn.utoverse.utoverselib.AbstractUtoverseLibPlugin;
import com.google.common.collect.EvictingQueue;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class Util {

    private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    private volatile static Boolean devMode = null;
    @Getter
    private static boolean disableDebugLogger = false;
    @SuppressWarnings("UnstableApiUsage")
    private static final EvictingQueue<String> DEBUG_LOGS = EvictingQueue.create(500);

    /**
     * 转换&为颜色符号
     *
     * @param text
     * @return
     */
    public static String parseColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Ensure this method is calling from specific thread
     *
     * @param async on async thread or main server thread.
     */
    public static void ensureThread(boolean async) {
        boolean isMainThread = Bukkit.isPrimaryThread();
        if (async) {
            if (isMainThread) {
                throw new IllegalStateException("#[Illegal Access] This method require runs on async thread.");
            }
        } else {
            if (!isMainThread) {
                throw new IllegalStateException("#[Illegal Access] This method require runs on server main thread.");
            }
        }
    }

    /**
     * Return the player names based on the configuration
     *
     * @return the player names
     */
    @NotNull
    public static List<String> getPlayerList() {
        return Bukkit.getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

    @NotNull
    public static List<String> getPlayerList(String filter) {
        return getPlayerList(filter, new ArrayList<>());
    }

    /**
     * Return the player names based on the configuration
     *
     * @return the player names
     */
    @NotNull
    public static List<String> getPlayerList(String filter, List<String> ignorePlayers) {
        List<String> tabList = getPlayerList();
        return tabList.stream().filter(s -> {
            return s.toLowerCase().startsWith(filter.toLowerCase()) && !(ignorePlayers.contains(s));
        }).toList();
    }

    /**
     * Converts a name like IRON_INGOT into Iron Ingot to improve readability
     *
     * @param ugly The string such as IRON_INGOT
     * @return A nicer version, such as Iron Ingot
     */
    @NotNull
    public static String prettifyText(@NotNull String ugly) {
        String[] nameParts = ugly.split("_");
        if (nameParts.length == 1) {
            return firstUppercase(ugly);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nameParts.length; i++) {
            if (!nameParts[i].isEmpty()) {
                sb.append(Character.toUpperCase(nameParts[i].charAt(0))).append(nameParts[i].substring(1).toLowerCase());
            }
            if (i + 1 != nameParts.length) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    /**
     * First uppercase for every words the first char for a text.
     *
     * @param string text
     * @return Processed text.
     */
    @NotNull
    public static String firstUppercase(@NotNull String string) {
        if (string.length() > 1) {
            return Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();
        } else {
            return string.toUpperCase();
        }
    }

    public static void mainThreadRun(@NotNull Runnable runnable) {
        if (Bukkit.isPrimaryThread()) {
            runnable.run();
        } else {
            Bukkit.getScheduler().runTask(AbstractUtoverseLibPlugin.getInstance(), runnable);
        }
    }
}
