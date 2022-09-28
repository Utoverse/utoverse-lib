package cn.utoverse.utoverselib.command;


import me.lucko.helper.Commands;
import me.lucko.helper.command.Command;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandBuilder;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import me.lucko.helper.command.functional.FunctionalTabHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public class CommandBuilder<T extends CommandSender> {

    private FunctionalCommandBuilder<T> luckoCmdBuilder = (FunctionalCommandBuilder<T>) Commands.create();

    public CommandBuilder<T> description(String var1) {
        luckoCmdBuilder.description(var1);
        return this;
    }

    public CommandBuilder<T> assertFunction(Predicate<? super CommandContext<? extends T>> var1) {
        luckoCmdBuilder.assertFunction(var1, (String) null);
        return this;
    }

    public CommandBuilder<T> assertPermission(String var1) {
        luckoCmdBuilder.assertPermission(var1, (String) null);
        return this;
    }

    public CommandBuilder<T> assertOp() {
        luckoCmdBuilder.assertOp("&c仅服务器管理员可执行此命令");
        return this;
    }

    public CommandBuilder<Player> assertPlayer() {
        luckoCmdBuilder.assertPlayer("&c仅普通玩家可执行此命令");
        return (CommandBuilder<Player>) this;
    }

    public CommandBuilder<ConsoleCommandSender> assertConsole() {
        luckoCmdBuilder.assertConsole("&c此命令只能通过服务器控制台使用。");
        return (CommandBuilder<ConsoleCommandSender>) this;
    }

    public CommandBuilder<T> assertUsage(String var1) {
        luckoCmdBuilder.assertUsage(var1, "&c参数无效. 用法: {usage}.");
        return this;
    }

    public CommandBuilder<T> assertArgument(int var1, Predicate<String> var2) {
        luckoCmdBuilder.assertArgument(var1, var2, "&c参数 '{arg}' 是非法的. &7({index})");
        return this;
    }

    public CommandBuilder<T> assertSender(Predicate<T> var1) {
        luckoCmdBuilder.assertSender(var1, "&c你无法使用此命令。");
        return this;
    }

    public CommandBuilder<T> onTabComplete(FunctionalTabHandler<T> var1) {
        luckoCmdBuilder.tabHandler(var1);
        return this;
    }

    public Command onCommand(FunctionalCommandHandler<T> var1) {
        luckoCmdBuilder.handler(var1);
        return luckoCmdBuilder.handler(var1);
    }

    public Command handler(FunctionalHandler<T> var1) {
        return luckoCmdBuilder
                .tabHandler(commandContext -> var1.onTabComplete(commandContext))
                .handler(commandContext -> {
                    try {
                        var1.onCommand(commandContext);
                    } catch (CommandInterruptException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                });
    }
}
