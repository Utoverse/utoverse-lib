package cn.utoverse.utoverselib.util;

import cn.utoverse.utoverselib.AbstractUtoverseLibPlugin;
import de.themoep.minedown.MineDown;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MsgUtil {
    @Getter
    @Setter
    private static List<String> prefixList = new ArrayList<>();

    public static String getMsgPrefix() {
        try {
            return prefixList.get(RandomUtils.nextInt(0, prefixList.size()));
        } catch (Exception e) {
            return Util.parseColor("&dUtoverse>>>");
        }
    }

    public static void sendDirectMessage(@NotNull UUID sender, @NotNull String messages) {
        sendDirectMessage(Bukkit.getPlayer(sender), true, messages);
    }

    public static <T extends CommandSender> void sendDirectMessage(T sender, @NotNull boolean format, @NotNull String message) {
        if (format) {
            sendDirectMessage(sender, MineDown.parse(message));
        } else {
            sendDirectMessage(sender, new TextComponent(message));
        }
    }

    public static <T extends CommandSender> void sendDirectMessage(T sender, @NotNull BaseComponent... msgComp) {
        String prefix = getMsgPrefix();
        BaseComponent[] baseComponents = new ComponentBuilder()
                .append(MineDown.parse(prefix), ComponentBuilder.FormatRetention.NONE)
                .append(msgComp, ComponentBuilder.FormatRetention.NONE)
                .create();

        sender.spigot().sendMessage(baseComponents);
    }

    public static void broadcast(@NotNull String... message) {
        broadcast(ChatMessageType.SYSTEM, message);
    }


    public static void broadcast(@NotNull ChatMessageType messageType, @NotNull String... message) {
        broadcast(messageType, (List<Player>) AbstractUtoverseLibPlugin.getInstance().getServer().getOnlinePlayers(), message);
    }

    public static void broadcast(@NotNull ChatMessageType messageType, @NotNull List<Player> playerCollection, @NotNull String... message) {
        ComponentBuilder componentBuilder = new ComponentBuilder("");
        for (int i = 0; i < message.length; i++) {
            componentBuilder.append(MineDown.parse(message[i]), ComponentBuilder.FormatRetention.NONE);
        }
        BaseComponent[] broadcastComp = componentBuilder.create();
        broadcast(messageType, broadcastComp, playerCollection);
    }

    public static void broadcast(@NotNull ChatMessageType messageType, @NotNull BaseComponent[] components) {
        broadcast(messageType, components, (List<Player>) AbstractUtoverseLibPlugin.getInstance().getServer().getOnlinePlayers());
    }

    public static void broadcast(@NotNull ChatMessageType messageType, @NotNull BaseComponent[] components, @NotNull List<Player> playerCollection) {
        ComponentBuilder componentBuilder = new ComponentBuilder("")
                .append(MineDown.parse(getMsgPrefix()), ComponentBuilder.FormatRetention.NONE)
                .append(ChatColor.RESET.toString())
                .append(components, ComponentBuilder.FormatRetention.NONE);

        BaseComponent[] broadcastComp = componentBuilder.create();

        playerCollection.forEach(player -> {
            player.spigot().sendMessage(ChatMessageType.SYSTEM, broadcastComp);
        });
    }

    public static void printToConsole(String msg) {
        Bukkit.getConsoleSender().sendMessage(Util.parseColor(msg));
    }

}
