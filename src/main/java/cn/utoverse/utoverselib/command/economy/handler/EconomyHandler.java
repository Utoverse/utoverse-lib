package cn.utoverse.utoverselib.command.economy.handler;

import cn.utoverse.utoverselib.command.FunctionalHandler;
import cn.utoverse.utoverselib.util.Util;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EconomyHandler implements FunctionalHandler<Player> {
    @Override
    public void onCommand(CommandContext<Player> c) throws CommandInterruptException {
        Player player = c.arg(1).parseOrFail(Player.class);
        String count = c.arg(2).parseOrFail(String.class);

        switch (c.arg(0).parseOrFail(String.class)) {
            case "give" -> {

            }
            case "take" -> {

            }
            case "set" -> {

            }
            case "reset" -> {

            }
            default -> {

            }
        }

    }

    @Override
    public List<String> onTabComplete(CommandContext<Player> context) throws CommandInterruptException {
        switch (context.args().size()) {
            case 1 -> {
                return Arrays.asList("give", "take", "set", "reset");
            }
            case 2 -> {
                return Util.getPlayerList(context.arg(0).parseOrFail(String.class));
            }
            case 3 -> {
                return Arrays.asList("数量");
            }
            default -> {
                return new ArrayList<>();
            }
        }
    }
}

