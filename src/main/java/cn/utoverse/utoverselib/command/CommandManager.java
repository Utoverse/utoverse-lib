package cn.utoverse.utoverselib.command;

import cn.utoverse.utoverselib.AbstractUtoverseLibPlugin;
import cn.utoverse.utoverselib.command.home.HomeCommandsModule;
import cn.utoverse.utoverselib.command.teleport.TeleportCommandsModule;

public class CommandManager {

    public CommandManager() {
        AbstractUtoverseLibPlugin.getInstance().bindModule(new TeleportCommandsModule());
        AbstractUtoverseLibPlugin.getInstance().bindModule(new HomeCommandsModule());
//        AbstractUtoverseLibPlugin.getInstance().bindModule(new EconomyCommandsModule());
    }
}