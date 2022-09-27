package cn.utoverse.utoverselib.listener;

import cn.utoverse.utoverselib.AbstractUtoverseLibPlugin;
import cn.utoverse.utoverselib.profile.UserProfile;
import lombok.AllArgsConstructor;
import me.lucko.helper.profiles.Profile;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;

@AllArgsConstructor
public class UserProfileListener implements Listener {

    private AbstractUtoverseLibPlugin plugin;

    private void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        try {
            Profile.create(event.getUniqueId(), event.getName());
            plugin.getLogger().info(String.format("Player %s[%s] profile are created.", event.getName(), event.getUniqueId().toString()));
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, String.format("Player %s[%s] profile throw an exception:", event.getName(), event.getUniqueId().toString()), e);
        }
    }

    private void onPlayerQuitEvent(PlayerQuitEvent event) {
        Profile profile = UserProfile.getUserProfile(event.getPlayer());
    }
}
