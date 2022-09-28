package cn.utoverse.utoverselib.command.home;

import cn.utoverse.utoverselib.command.CommandBuilder;
import cn.utoverse.utoverselib.command.home.handler.*;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.jetbrains.annotations.NotNull;

public class HomeCommandsModule implements TerminableModule {
    @Override
    public void setup(@NotNull TerminableConsumer consumer) {
        new CommandBuilder()
                .assertPlayer()
                .assertPermission("utolib.command.home")
                .assertUsage("<name>")
                .description("传送到设置的家")
                .handler(new HomeHandler())
                .registerAndBind(consumer, "home");

        new CommandBuilder()
                .assertPlayer()
                .assertPermission("utolib.command.sethome")
                .assertUsage("<name>")
                .description("设置一个家")
                .handler(new SethomeHandler())
                .registerAndBind(consumer, "sethome");

        new CommandBuilder()
                .assertPlayer()
                .assertPermission("utolib.command.delhome")
                .assertUsage("<name>")
                .description("删除一个设置的家")
                .handler(new DelhomeHandler())
                .registerAndBind(consumer, "delhome");
    }
}
