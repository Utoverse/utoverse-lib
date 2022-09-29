package cn.utoverse.utoverselib.api.util;

import cn.utoverse.utoverselib.util.TeleportUtil;
import ink.tuanzi.utoverselib.util.ITeleport;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.concurrent.CompletableFuture;

public class ApiTeleportUtil implements ITeleport {
    @Override
    public CompletableFuture<Boolean> teleportAsync(Entity entity, Location location) {
        return TeleportUtil.teleportAsync(entity, location);
    }

    @Override
    public CompletableFuture<Boolean> teleportAsync(Entity entity, Location location, PlayerTeleportEvent.TeleportCause teleportCause) {
        return TeleportUtil.teleportAsync(entity, location, teleportCause);
    }
}
