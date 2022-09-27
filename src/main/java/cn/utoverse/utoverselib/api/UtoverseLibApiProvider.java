package cn.utoverse.utoverselib.api;

import cn.utoverse.utoverselib.AbstractUtoverseLibPlugin;
import cn.utoverse.utoverselib.api.util.ApiTeleportUtil;
import cn.utoverse.utoverselibapi.UtoverseLib;
import cn.utoverse.utoverselibapi.util.Teleport;
import org.jetbrains.annotations.NotNull;

public class UtoverseLibApiProvider implements UtoverseLib {
    private AbstractUtoverseLibPlugin plugin;

    public UtoverseLibApiProvider(AbstractUtoverseLibPlugin plugin) {
        this.plugin = plugin;
    }

    @NotNull
    public static AbstractUtoverseLibPlugin getInstance() {
        return AbstractUtoverseLibPlugin.getInstance();
    }

    @Override
    public Teleport getTeleportUtil() {
        return new ApiTeleportUtil();
    }
}
