package cn.utoverse.utoverselib.command;

import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public interface FunctionalHandler<T extends CommandSender> {
    void onCommand(CommandContext<T> context) throws CommandInterruptException;

    default List<String> onTabComplete(CommandContext<T> context) throws CommandInterruptException {
       return new ArrayList<>();
    }
}
