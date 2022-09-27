package cn.utoverse.utoverselib.util.message;

import lombok.Getter;
import org.bukkit.ChatColor;

public enum MessageLevel {
    INFO(ChatColor.GOLD.toString()),
    NONE(ChatColor.RESET + ChatColor.WHITE.toString()),
    WARN(ChatColor.RED.toString());

    @Getter
    private String chatColor;

    MessageLevel(String color) {
        this.chatColor = color;
    }
}
