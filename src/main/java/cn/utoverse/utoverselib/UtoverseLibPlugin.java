package cn.utoverse.utoverselib;

import cn.utoverse.utoverselib.api.ApiRegistrationUtil;
import cn.utoverse.utoverselib.api.UtoverseLibApiImpl;
import cn.utoverse.utoverselib.command.CommandManager;
import cn.utoverse.utoverselib.database.DatabaseCoreImpl;
import cn.utoverse.utoverselib.database.DatabaseProvider;
import cn.utoverse.utoverselib.database.IDatabaseCore;
import cn.utoverse.utoverselib.profile.UserProfileRepo;
import cn.utoverse.utoverselib.profile.listener.ProfileListener;
import cn.utoverse.utoverselib.util.MsgUtil;
import cn.utoverse.utoverselib.util.config.Configuration;
import ink.tuanzi.utoverselib.IUtoverseLib;
import ink.tuanzi.utoverselib.constant.ConfigFile;
import lombok.Getter;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.plugin.ServicePriority;
import org.jetbrains.annotations.NotNull;

public class UtoverseLibPlugin extends ExtendedJavaPlugin implements DatabaseProvider {

    @Getter
    private static transient UtoverseLibPlugin instance;
    @Getter
    private UtoverseLibApiImpl apiProvider;
    private DatabaseCoreImpl databaseCore;

    @Override
    protected void enable() {
        instance = this;
        Configuration.init();
        MsgUtil.setPrefixList(Configuration.getFiles().get(ConfigFile.CONFIG).getStringList("msg-prefix"));

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

        setupDatabase();

        new CommandManager();
        this.bindModule(new ProfileListener(this));
        this.bindModule(new UserProfileRepo());

        registerApiProvider();

        super.enable();
    }

    @Override
    protected void disable() {
        if (this.databaseCore != null) {
            this.databaseCore.close();
        }
    }

    /**
     * Register UtoverseLib API
     */
    private void registerApiProvider() {
        this.apiProvider = new UtoverseLibApiImpl(this);
        ApiRegistrationUtil.registerProvider(this.apiProvider);
        this.getServer().getServicesManager().register(IUtoverseLib.class, this.apiProvider, this, ServicePriority.Normal);
    }

    private void setupDatabase() {
        getLogger().info("Setting up database...");

        this.databaseCore = new DatabaseCoreImpl();
        this.databaseCore.bindWith(this);
        provideService(IDatabaseCore.class, this.databaseCore);
    }

    @NotNull
    @Override
    public IDatabaseCore getCore() {
        return this.databaseCore;
    }
}
