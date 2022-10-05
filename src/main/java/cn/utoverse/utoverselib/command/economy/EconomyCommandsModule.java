package cn.utoverse.utoverselib.command.economy;

import cn.utoverse.utoverselib.command.CommandBuilder;
import cn.utoverse.utoverselib.command.economy.handler.EconomyHandler;
import cn.utoverse.utoverselib.command.economy.handler.PayHandler;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.jetbrains.annotations.NotNull;

public class EconomyCommandsModule implements TerminableModule {
    @Override
    public void setup(@NotNull TerminableConsumer consumer) {
        new CommandBuilder()
                .assertPlayer()
                .assertPermission("utolib.command.economy")
                .assertUsage("[give|take|set|reset] [player] [count]")
                .description("管理服务器的经济")
                .handler(new EconomyHandler())
                .registerAndBind(consumer, "economy", "econ");

        new CommandBuilder()
                .assertPlayer()
                .assertPermission("utolib.command.pay")
                .assertUsage("<name>")
                .description("向玩家支付货币")
                .handler(new PayHandler())
                .registerAndBind(consumer, "pay");

    }
}
