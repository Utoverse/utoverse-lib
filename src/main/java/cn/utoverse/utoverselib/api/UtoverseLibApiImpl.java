package cn.utoverse.utoverselib.api;

import cn.utoverse.utoverselib.AbstractUtoverseLibPlugin;
import cn.utoverse.utoverselib.api.profile.ApiUserProfile;
import cn.utoverse.utoverselib.api.util.ApiTeleportUtil;
import ink.tuanzi.utoverselib.IUtoverseLib;
import ink.tuanzi.utoverselib.profile.UserProfileRepo;
import ink.tuanzi.utoverselib.util.ITeleport;
import org.jetbrains.annotations.NotNull;

public class UtoverseLibApiImpl implements IUtoverseLib {
    private AbstractUtoverseLibPlugin plugin;

    public UtoverseLibApiImpl(AbstractUtoverseLibPlugin plugin) {
        this.plugin = plugin;
    }

    @NotNull
    public static AbstractUtoverseLibPlugin getInstance() {
        return AbstractUtoverseLibPlugin.getInstance();
    }

    @Override
    public ITeleport getTeleportUtil() {
        return new ApiTeleportUtil();
    }

    @Override
    public UserProfileRepo getUserProfileUtil() {
        return new ApiUserProfile();
    }
}
