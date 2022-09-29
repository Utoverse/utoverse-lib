package cn.utoverse.utoverselib.command.home.handler;

import cn.utoverse.utoverselib.command.FunctionalHandler;
import cn.utoverse.utoverselib.profile.UserProfileRepo;
import cn.utoverse.utoverselib.util.MsgUtil;
import cn.utoverse.utoverselib.util.message.MessageBuilder;
import ink.tuanzi.utoverselib.profile.UserProfile;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DelhomeHandler implements FunctionalHandler<Player> {
    @Override
    public void onCommand(CommandContext<Player> c) throws CommandInterruptException {
        String homeName = c.arg(0).parseOrFail(String.class);

        UserProfile userProfile = UserProfileRepo.getProfile(c.sender());
        HashMap<String, Location> homes = userProfile.getHomes();

        if (!homes.containsKey(homeName)) {
            MsgUtil.sendDirectMessage(
                    c.sender(),
                    new MessageBuilder().warn().append("家").space().append("&d" + homeName).space().append("不存在").build()
            );
            return;
        }

        UserProfile userProfile1 = (UserProfile) userProfile.clone();
        userProfile1.getHomes().remove(homeName);
        cn.utoverse.utoverselib.profile.UserProfile.update(userProfile1);

        MsgUtil.sendDirectMessage(
                c.sender(),
                new MessageBuilder().info().append("已删除家").space().append("&d" + homeName).build()
        );
    }

    @Override
    public List<String> onTabComplete(CommandContext<Player> context) throws CommandInterruptException {
        switch (context.args().size()) {
            case 1 -> {
                UserProfile userProfile = UserProfileRepo.getProfile(context.sender());
                return userProfile.getHomes().keySet().stream().toList();
            }
            default -> {
                return new ArrayList<>();
            }
        }
    }
}
