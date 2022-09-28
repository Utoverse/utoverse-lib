package cn.utoverse.utoverselib.profile.listener.handler;

import cn.utoverse.utoverselib.AbstractUtoverseLibPlugin;
import cn.utoverse.utoverselib.profile.UserProfile;
import cn.utoverse.utoverselib.profile.UserProfileRepo;
import cn.utoverse.utoverselib.profile.account.Account;
import lombok.AllArgsConstructor;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Date;
import java.util.function.Consumer;
import java.util.logging.Level;

@AllArgsConstructor
public class JoinEventHandler implements Consumer<PlayerJoinEvent> {

    private AbstractUtoverseLibPlugin plugin;

    @Override
    public void accept(PlayerJoinEvent event) {
        try {
            if (event.getPlayer().hasPlayedBefore()) {
                Account account = UserProfileRepo.getProfile(event.getPlayer().getName()).clone();
                account.getAccountTimes().setLogin(new Date().getTime());
                UserProfile.update(account);
            } else {
                UserProfile.create(event.getPlayer(), new Date().getTime());
                plugin.getLogger().info(String.format("Player %s[%s] profile are created.", event.getPlayer().getName(), event.getPlayer().getUniqueId().toString()));
            }
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, String.format("Player %s[%s] profile throw an exception:", event.getPlayer().getName(), event.getPlayer().getUniqueId().toString()), e);
        }
    }
}
