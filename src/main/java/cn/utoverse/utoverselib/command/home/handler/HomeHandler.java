package cn.utoverse.utoverselib.command.home.handler;

import cn.utoverse.utoverselib.command.FunctionalHandler;
import cn.utoverse.utoverselib.profile.UserProfileRepo;
import cn.utoverse.utoverselib.profile.account.Account;
import cn.utoverse.utoverselib.util.MsgUtil;
import cn.utoverse.utoverselib.util.TeleportUtil;
import cn.utoverse.utoverselib.util.config.ConfigFile;
import cn.utoverse.utoverselib.util.config.Configuration;
import cn.utoverse.utoverselib.util.message.MessageBuilder;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeHandler implements FunctionalHandler<Player> {
    @Override
    public void onCommand(CommandContext<Player> c) throws CommandInterruptException {
        if (c.args().isEmpty()) {
            Location location = c.sender().getBedSpawnLocation();
            if (location == null) {
                YamlConfiguration yaml = Configuration.getFiles().get(ConfigFile.CONFIG);
                String spawnWorld = yaml.getString("spawn-world", "world");
                location = Bukkit.getWorld(spawnWorld).getSpawnLocation();
            }

            TeleportUtil.teleportAsync(c.sender(), location);
            MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().info().append("正在传送...").build());
        } else {
            String homeName = c.arg(0).parse(String.class).get();
            Account account = UserProfileRepo.getProfile(c.sender());
            HashMap<String, Location> homes = account.getHomes();

            if (!homes.containsKey(homeName)) {
                MsgUtil.sendDirectMessage(
                        c.sender(),
                        new MessageBuilder().warn().append("家").space().append("&d" + homeName).space().append("不存在").build()
                );
                return;
            }

            TeleportUtil.teleportAsync(c.sender(), homes.get(homeName));
            MsgUtil.sendDirectMessage(c.sender(), new MessageBuilder().info().append("正在传送...").build());
        }
    }

    @Override
    public List<String> onTabComplete(CommandContext<Player> context) throws CommandInterruptException {
        switch (context.args().size()) {
            case 1 -> {
                Account account = UserProfileRepo.getProfile(context.sender());
                return account.getHomes().keySet().stream().toList();
            }
            default -> {
                return new ArrayList<>();
            }
        }
    }
}
