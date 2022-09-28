package cn.utoverse.utoverselib.command.teleport.handler;

import cn.utoverse.utoverselib.command.FunctionalHandler;
import cn.utoverse.utoverselib.profile.UserProfileRepo;
import cn.utoverse.utoverselib.profile.account.Account;
import cn.utoverse.utoverselib.profile.account.TeleportReason;
import cn.utoverse.utoverselib.profile.account.TeleportSession;
import cn.utoverse.utoverselib.util.MsgUtil;
import cn.utoverse.utoverselib.util.TeleportUtil;
import cn.utoverse.utoverselib.util.config.ConfigFile;
import cn.utoverse.utoverselib.util.config.Configuration;
import cn.utoverse.utoverselib.util.message.MessageBuilder;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class TpacceptHandler implements FunctionalHandler<Player> {

    @Override
    public void onCommand(CommandContext<Player> c) throws CommandInterruptException {
        // 指定玩家/全部接受传送会话
        if (c.args().size() == 1) {
            if (c.arg(0).parse(String.class).orElse("").equals("*")) {
                // 接受所有传送请求
                Account senderAccount = UserProfileRepo.getProfile(c.sender());
                if (senderAccount.getTeleportSessions().isEmpty()) {
                    MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().warn().append("你没有待处理的传送请求").build());
                    return;
                }

                acceptPlayerTeleport(c.sender(), senderAccount.getTeleportSessions().values().stream().toList());
            } else {
                // 接受指定玩家的传送
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

                acceptPlayerTeleport(c.sender(), Arrays.asList(teleportSession));
            }
        } else {
            // 接受最近一次的传送
            Account senderAccount = UserProfileRepo.getProfile(c.sender());
            if (senderAccount.getTeleportSessions().isEmpty()) {
                MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().warn().append("你没有待处理的传送请求").build());
                return;
            }
            List<TeleportSession> teleportSessions = senderAccount.getTeleportSessions().values().stream().toList();
            acceptPlayerTeleport(c.sender(), Arrays.asList(teleportSessions.get(teleportSessions.size() - 1)));
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

    private void acceptPlayerTeleport(Player sender, List<TeleportSession> sessions) {
        YamlConfiguration yaml = Configuration.getFiles().get(ConfigFile.CONFIG);
        int duration = yaml.getInt("teleport-session-duration", 120);

        if (sessions != null) {
            List<TeleportSession> acceptedSessions = new ArrayList<>();
            sessions.forEach(session -> {
                long nowDate = new Date().getTime();
                long createTime = session.getCreateTime().getTime();

                int diffSeconds = (int) ((nowDate - createTime) / 1000);

                if (diffSeconds < duration) {
                    if (session.getRequester().isOnline()) {
                        if (TeleportReason.TPA.equals(session.getReason())) {
                            TeleportUtil.teleportAsync(session.getRequester(), sender.getLocation());
                        } else if (TeleportReason.TPAHERE.equals(session.getReason())) {
                            TeleportUtil.teleportAsync(sender, session.getLocation());
                        }
                    }
                    MsgUtil.sendDirectMessage(session.getRequester(), new MessageBuilder().info().append("正在传送至").space().appendEntity(sender).build());
                    acceptedSessions.add(session);
                }
                Account senderAccount = UserProfileRepo.getProfile(sender);
                senderAccount.getTeleportSessions().remove(session.getRequester().getName());
            });

            if (!acceptedSessions.isEmpty()) {
                MsgUtil.sendDirectMessage(sender, new MessageBuilder().info().append("已接受传送传送请求").build());
            }
            if (sessions.size() != acceptedSessions.size()) {
                MsgUtil.sendDirectMessage(sender, new MessageBuilder().warn().append("你没有待处理的传送请求").build());
            }
        }
    }
}