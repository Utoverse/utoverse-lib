package cn.utoverse.utoverselib.command.home.handler;

import cn.utoverse.utoverselib.command.FunctionalHandler;
import cn.utoverse.utoverselib.profile.UserProfile;
import cn.utoverse.utoverselib.profile.UserProfileRepo;
import cn.utoverse.utoverselib.profile.account.Account;
import cn.utoverse.utoverselib.util.MsgUtil;
import cn.utoverse.utoverselib.util.message.MessageBuilder;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SethomeHandler implements FunctionalHandler<Player> {
    @Override
    public void onCommand(CommandContext<Player> c) throws CommandInterruptException {
        String homeName = c.arg(0).parseOrFail(String.class);

        Account account = UserProfileRepo.getProfile(c.sender());
        HashMap<String, Location> homes = account.getHomes();

        if (homes.containsKey(homeName)) {
            MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().warn().append("家").space().append("&d" + homeName).space().append("已存在，为防止被覆盖请先手动删除它").build());
            return;
        }

        Account account1 = account.clone();
        account1.getHomes().put(homeName, c.sender().getLocation());
        UserProfile.update(account1);

        MsgUtil.sendDirectMessage(
                c.sender(),
                new MessageBuilder().info().append("已设置家").space().append("&d" + homeName).build()
        );
    }
}
