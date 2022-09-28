package cn.utoverse.utoverselib.command.teleport.handler;

import cn.utoverse.utoverselib.command.FunctionalHandler;
import cn.utoverse.utoverselib.profile.UserProfileRepo;
import cn.utoverse.utoverselib.profile.account.Account;
import cn.utoverse.utoverselib.profile.account.TeleportSession;
import cn.utoverse.utoverselib.util.MsgUtil;
import cn.utoverse.utoverselib.util.message.MessageBuilder;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TpdenyHandler implements FunctionalHandler<Player> {

    @Override
    public void onCommand(CommandContext<Player> c) throws CommandInterruptException {
        // 拒绝玩家/全部接受传送会话
        if (c.args().size() == 1) {
            if (c.arg(0).parse(String.class).orElse("").equals("*")) {
                // 接受所有传送请求
                Account senderAccount = UserProfileRepo.getProfile(c.sender());
                if (senderAccount.getTeleportSessions().isEmpty()) {
                    MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().warn().append("你没有待处理的传送请求").build());
                    return;
                }
                senderAccount.getTeleportSessions().forEach((name, session) -> {
                    MsgUtil.sendDirectMessage(session.getRequester(), new MessageBuilder().info().appendEntity(c.sender()).space().append("拒绝了你的传送请求").build());
                });
                senderAccount.getTeleportSessions().clear();
                MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().info().append("已拒绝所有待处理的传送请求").build());

            } else {
                // 拒绝指定玩家的传送
                Optional<Player> optionalPlayer = c.arg(0).parse(Player.class);
                if (optionalPlayer.isEmpty()) {
                    MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().warn().append("你没有待处理的传送请求").build());
                    return;
                }
                Player other = optionalPlayer.get();

                Account senderAccount = UserProfileRepo.getProfile(c.sender());
                TeleportSession teleportSession = senderAccount.getTeleportSessions().get(other.getName());

                if (teleportSession == null) {
                    MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().warn().append("你没有待处理的传送请求").build());
                    return;
                }

                MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().info().append("已拒绝传送请求").build());
                MsgUtil.sendDirectMessage(other, new MessageBuilder().info().appendEntity(other).space().append("拒绝了你的传送请求").build());
                senderAccount.getTeleportSessions().remove(other.getName());
            }
        } else {
            // 拒绝最近一次的传送
            try {
                Account senderAccount = UserProfileRepo.getProfile(c.sender());
                String lastKey = senderAccount.getTeleportSessions().lastKey();
                TeleportSession lastTpSession = senderAccount.getTeleportSessions().get(lastKey);
                senderAccount.getTeleportSessions().remove(lastKey);
                MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().info().append("已拒绝传送请求").build());
                MsgUtil.sendDirectMessage(lastTpSession.getRequester(), new MessageBuilder().info().appendEntity(c.sender()).append("拒绝了你的传送请求").build());
            } catch (Exception e) {
                MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().warn().append("你没有待处理的传送请求").build());
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandContext<Player> context) throws CommandInterruptException {
        switch (context.args().size()) {
            case 1 -> {
                Account senderAccount = UserProfileRepo.getProfile(context.sender());
                List<String> list = new ArrayList<>();
                list.add("*");
                list.addAll(senderAccount.getTeleportSessions().keySet());
                String keyword = context.arg(0).parseOrFail(String.class).toLowerCase();
                return list.stream().filter(s -> s.toLowerCase().startsWith(keyword)).toList();
            }
            default -> {
                return new ArrayList<>();
            }
        }
    }
}