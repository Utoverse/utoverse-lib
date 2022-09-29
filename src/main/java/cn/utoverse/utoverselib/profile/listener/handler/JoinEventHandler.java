package cn.utoverse.utoverselib.profile.listener.handler;

import cn.utoverse.utoverselib.UtoverseLibPlugin;
import cn.utoverse.utoverselib.profile.UserProfileRepo;
import ink.tuanzi.utoverselib.profile.UserProfile;
import lombok.AllArgsConstructor;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Date;
import java.util.function.Consumer;
import java.util.logging.Level;

@AllArgsConstructor
public class JoinEventHandler implements Consumer<PlayerJoinEvent> {

    private UtoverseLibPlugin plugin;

    @Override
    public void accept(PlayerJoinEvent event) {
        try {
            if (event.getPlayer().hasPlayedBefore()) {
                UserProfile userProfile = (UserProfile) UserProfileRepo.getProfile(event.getPlayer().getName()).clone();
                userProfile.getUserProfileTimestamps().setLogin(new Date().getTime());
                cn.utoverse.utoverselib.profile.UserProfile.update(userProfile);
            } else {
                cn.utoverse.utoverselib.profile.UserProfile.create(event.getPlayer(), new Date().getTime());
                plugin.getLogger().info(String.format("Player %s[%s] profile are created.", event.getPlayer().getName(), event.getPlayer().getUniqueId()));
            }
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, String.format("Player %s[%s] create/load profile throw an exception:", event.getPlayer().getName(), event.getPlayer().getUniqueId()));
            e.printStackTrace();
        }
    }
}
