package cn.utoverse.utoverselib;

import cn.utoverse.utoverselib.api.ApiRegistrationUtil;
import cn.utoverse.utoverselib.api.UtoverseLibApiProvider;
import cn.utoverse.utoverselib.command.CommandManager;
import cn.utoverse.utoverselib.profile.listener.ProfileListener;
import cn.utoverse.utoverselib.util.MsgUtil;
import cn.utoverse.utoverselib.util.config.ConfigFile;
import cn.utoverse.utoverselib.util.config.Configuration;
import cn.utoverse.utoverselibapi.UtoverseLib;
import lombok.Getter;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.apache.commons.lang.StringUtils;
import org.bukkit.plugin.ServicePriority;

public class AbstractUtoverseLibPlugin extends ExtendedJavaPlugin implements UtoverseLibPlugin {

    @Getter
    private static transient AbstractUtoverseLibPlugin instance;
    @Getter
    private static Configuration configuration;
    @Getter
    private UtoverseLibApiProvider apiProvider;

    @Override
    protected void enable() {
        instance = this;
        configuration = new Configuration(this);
        MsgUtil.setPrefixList(configuration.getFiles().get(ConfigFile.CONFIG).getStringList("msg-prefix"));

        MsgUtil.printToConsole("&e-----------------------");
        MsgUtil.printToConsole("      _   _ _                           _    _ _        ");
        MsgUtil.printToConsole("     | | | | |_ _____ _____ _ _ ___ ___| |  (_) |__     ");
        MsgUtil.printToConsole("     | |_| |  _/ _ \\ V / -_) '_(_-</ -_) |__| | '_ \\    ");
        MsgUtil.printToConsole("      \\___/ \\__\\___/\\_/\\___|_| /__/\\___|____|_|_.__/    ");
        MsgUtil.printToConsole("                                                        ");
        MsgUtil.printToConsole("&bUtoverseLib &dDevelop by " + StringUtils.join(this.getDescription().getAuthors(), ", "));
        MsgUtil.printToConsole("&bPlugin verion: " + getDescription().getVersion());
        MsgUtil.printToConsole("&bServer version: " + getServer().getVersion());
        MsgUtil.printToConsole("&cSite: " + getDescription().getWebsite());
        MsgUtil.printToConsole("&e-----------------------");

        new CommandManager();
        this.bindModule(new ProfileListener(this));

        registerApiProvider();

        super.enable();
    }

    @Override
    protected void disable() {
    }

    /**
     * Register UtoverseLib API
     */
    private void registerApiProvider() {
        this.apiProvider = new UtoverseLibApiProvider(this);
        ApiRegistrationUtil.registerProvider(this.apiProvider);
        this.getServer().getServicesManager().register(UtoverseLib.class, this.apiProvider, this, ServicePriority.Normal);
    }
}
