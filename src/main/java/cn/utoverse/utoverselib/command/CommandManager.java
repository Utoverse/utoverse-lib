package cn.utoverse.utoverselib.command;

import cn.utoverse.utoverselib.UtoverseLibPlugin;
import cn.utoverse.utoverselib.command.home.HomeCommandsModule;
import cn.utoverse.utoverselib.command.teleport.TeleportCommandsModule;

public class CommandManager {

    public CommandManager() {
        UtoverseLibPlugin.getInstance().bindModule(new TeleportCommandsModule());
        UtoverseLibPlugin.getInstance().bindModule(new HomeCommandsModule());
//        AbstractUtoverseLibPlugin.getInstance().bindModule(new EconomyCommandsModule());
    }
}