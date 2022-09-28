package cn.utoverse.utoverselib.command.teleport.handler;

import cn.utoverse.utoverselib.command.FunctionalHandler;
import cn.utoverse.utoverselib.profile.UserProfileRepo;
import cn.utoverse.utoverselib.profile.account.TeleportSession;
import cn.utoverse.utoverselib.util.MsgUtil;
import cn.utoverse.utoverselib.util.Util;
import cn.utoverse.utoverselib.util.config.ConfigFile;
import cn.utoverse.utoverselib.util.config.Configuration;
import cn.utoverse.utoverselib.util.message.MessageBuilder;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class TpaHandler implements FunctionalHandler<Player> {

    @Override
    public void onCommand(CommandContext<Player> c) throws CommandInterruptException {
        String playerName = c.arg(0).parse(String.class).orElse("");
        if (c.sender().getName().equalsIgnoreCase(playerName)) {
            MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().warn().append("你不能向自己发送传送请求").build());
            return;
        }

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

        YamlConfiguration yaml = Configuration.getFiles().get(ConfigFile.CONFIG);
        int duration = yaml.getInt("teleport-session-duration", 120);

        BaseComponent[] msgComp = new MessageBuilder().info().append("玩家").space().appendEntity(c.sender()).space()
                .append("请求传送到你这里").enter()
                .append("若想接受传送，输入").appendCommand("&9/tpaccept", "/tpaccept").enter()
                .append("若想拒绝传送，输入").appendCommand("&9/tpdeny", "/tpdeny").enter()
                .append("此请求将在").append("&9" + duration + "秒").append("后自动取消")
                .build();

        UserProfileRepo.getProfile(other).getTeleportSessions().put(
                c.sender().getName(),
                TeleportSession.builder().requester(c.sender()).createTime(new Date()).location(c.sender().getLocation()).build()
        );

        MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().info().append("请求已发送给").space().appendEntity(other).build());
        MsgUtil.sendDirectMessage(other, msgComp);
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
