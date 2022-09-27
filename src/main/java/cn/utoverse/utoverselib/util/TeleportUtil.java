package cn.utoverse.utoverselib.util;

import cn.utoverse.utoverselibapi.util.Teleport;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.concurrent.CompletableFuture;

public class TeleportUtil  {

    public static CompletableFuture<Boolean> teleportAsync(Entity entity, Location location) {
        return teleportAsync(entity, location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    public static CompletableFuture<Boolean> teleportAsync(Entity entity, Location location, PlayerTeleportEvent.TeleportCause teleportCause) {
        return PaperLib.teleportAsync(entity, location);
    }
}
