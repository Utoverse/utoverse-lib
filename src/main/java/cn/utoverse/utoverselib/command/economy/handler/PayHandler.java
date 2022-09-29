package cn.utoverse.utoverselib.command.economy.handler;

import cn.utoverse.utoverselib.command.FunctionalHandler;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import org.bukkit.entity.Player;

import java.util.List;

public class PayHandler implements FunctionalHandler<Player> {
    @Override
    public void onCommand(CommandContext<Player> context) throws CommandInterruptException {

    }

    @Override
    public List<String> onTabComplete(CommandContext<Player> context) throws CommandInterruptException {
        return FunctionalHandler.super.onTabComplete(context);
    }
}
