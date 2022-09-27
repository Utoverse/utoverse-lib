package cn.utoverse.utoverselib.command.teleport;

import cn.utoverse.utoverselib.command.CommandBuilder;
import cn.utoverse.utoverselib.command.teleport.handler.TeleportPlayerHandler;
import cn.utoverse.utoverselib.command.teleport.handler.TeleportPosHandler;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;

import javax.annotation.Nonnull;

public class TeleportCommandsModule implements TerminableModule {

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        new CommandBuilder()
                .assertPlayer()
                .assertPermission("utolib.command.tp")
                .assertUsage("<player>")
                .description("传送至另一玩家身边")
                .handler(new TeleportPlayerHandler())
                .registerAndBind(consumer, "tp");

        new CommandBuilder()
                .assertPlayer()
                .assertPermission("utolib.command.tppos")
                .assertUsage("[x] [y] [z] [yaw] [pitch] [world]")
                .description("传送至指定坐标处")
                .handler(new TeleportPosHandler())
                .registerAndBind(consumer, "tppos");
    }
}
