package cn.utoverse.utoverselib.command.teleport.handler;

import cn.utoverse.utoverselib.command.FunctionalHandler;
import cn.utoverse.utoverselib.util.MsgUtil;
import cn.utoverse.utoverselib.util.TeleportUtil;
import cn.utoverse.utoverselib.util.Util;
import cn.utoverse.utoverselib.util.message.MessageBuilder;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TphereHandler implements FunctionalHandler<Player> {
    @Override
    public void onCommand(CommandContext<Player> c) throws CommandInterruptException {
        Optional<Player> optionalPlayer = c.arg(0).parse(Player.class);
        if (optionalPlayer.isEmpty()) {
            MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().warn().append("玩家不存在").build());
            return;
        }

        Player other = optionalPlayer.get();
        if (!other.isOnline()) {
            MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().warn().append("玩家").space().appendEntity(other).space().append("不存在").build());
            return;
        }

        MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().info().append("正在传送...").build());
        MsgUtil.sendDirectMessage(other, new MessageBuilder().info().append("正在传送...").build());
        TeleportUtil.teleportAsync(other, c.sender().getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
    }

    @Override
    public List<String> onTabComplete(CommandContext<Player> context) throws CommandInterruptException {
        switch (context.args().size()) {
            case 1 -> {
                return Util.getPlayerList(context.arg(0).parseOrFail(String.class), Arrays.asList(context.sender().getName()));
            }
            default -> {
                return new ArrayList<>();
            }
        }
    }
}
