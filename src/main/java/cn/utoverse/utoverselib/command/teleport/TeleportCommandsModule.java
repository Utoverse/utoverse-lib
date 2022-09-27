package cn.utoverse.utoverselib.command.teleport;

import cn.utoverse.utoverselib.command.CommandBuilder;
import cn.utoverse.utoverselib.command.teleport.handler.TpHandler;
import cn.utoverse.utoverselib.command.teleport.handler.TpaHandler;
import cn.utoverse.utoverselib.command.teleport.handler.Tppos_Handler;
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
                .description("传送至另一玩家的位置")
                .handler(new TpHandler())
                .registerAndBind(consumer, "tp");

        new CommandBuilder()
                .assertPlayer()
                .assertPermission("utolib.command.tppos")
                .assertUsage("[x] [y] [z] [yaw] [pitch] [world]")
                .description("传送至指定坐标/视角/世界")
                .handler(new Tppos_Handler())
                .registerAndBind(consumer, "tppos");

        new CommandBuilder()
                .assertPlayer()
                .assertPermission("utolib.command.tpa")
                .assertUsage("<player>")
                .description("请求传送至另一玩家的位置")
                .handler(new TpaHandler())
                .registerAndBind(consumer, "tpa");
    }
}
