package cn.utoverse.utoverselib.command.teleport.handler;

import cn.utoverse.utoverselib.command.FunctionalHandler;
import cn.utoverse.utoverselib.util.MsgUtil;
import cn.utoverse.utoverselib.util.TeleportUtil;
import cn.utoverse.utoverselib.util.message.MessageBuilder;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tppos_Handler implements FunctionalHandler<Player> {

    @Override
    public void onCommand(CommandContext<Player> c) throws CommandInterruptException {
        Double x = c.arg(0).parse(Double.class).orElse(c.sender().getLocation().getX());
        Double y = c.arg(1).parse(Double.class).orElse(c.sender().getLocation().getY());
        Double z = c.arg(2).parse(Double.class).orElse(c.sender().getLocation().getZ());
        Location loc = null;

        if (c.args().size() == 3) {
            loc = new Location(c.sender().getWorld(), x, y, z);
        } else {
            Float yaw = c.arg(3).parse(Float.class).orElse(0f);
            Float pitch = c.arg(4).parse(Float.class).orElse(0f);
            String world = c.arg(5).parse(String.class).orElse(c.sender().getWorld().getName());

            World world1 = Bukkit.getWorld(world);
            if (world1 == null) {
                MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().warn().append("世界" + world + "不存在").build());
                return;
            }

            loc = new Location(world1, x, y, z, yaw, pitch);
        }

        BaseComponent[] msgComp = new MessageBuilder().info().append("正在传送...").build();
        MsgUtil.sendDirectMessage(c.sender(), msgComp);

        TeleportUtil.teleportAsync(c.sender(), loc, PlayerTeleportEvent.TeleportCause.COMMAND);
    }

    @Override
    public List<String> onTabComplete(CommandContext<Player> context) throws CommandInterruptException {
        System.out.println(context.args());
        switch (context.args().size()) {
            case 1, 2, 3, 4, 5 -> {
                return Arrays.asList("0");
            }
            case 6 -> {
                return Bukkit.getWorlds().stream().map(World::getName).toList();
            }
            default -> {
                return new ArrayList<>();
            }
        }
    }
}

