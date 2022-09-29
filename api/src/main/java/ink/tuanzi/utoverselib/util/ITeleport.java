package ink.tuanzi.utoverselib.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.concurrent.CompletableFuture;

/**
 * 传送工具类
 */
public interface ITeleport {
    /**
     * 异步传送一个生物实体到某处
     *
     * @param entity 生物实体
     * @param location 传送点
     * @return 传送结果
     */
    CompletableFuture<Boolean> teleportAsync(Entity entity, Location location);

    /**
     * 异步传送一个生物实体到某处
     *
     * @param entity  生物实体
     * @param location 传送点
     * @param teleportCause 传送原因
     * @return 传送结果
     */
    CompletableFuture<Boolean> teleportAsync(Entity entity, Location location, PlayerTeleportEvent.TeleportCause teleportCause);

}
