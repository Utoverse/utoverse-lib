package cn.utoverse.utoverselib.profile.listener.handler;

import cn.utoverse.utoverselib.UtoverseLibPlugin;
import cn.utoverse.utoverselib.profile.UserProfileRepo;
import ink.tuanzi.utoverselib.profile.UserProfile;
import lombok.AllArgsConstructor;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.function.Consumer;

@AllArgsConstructor
public class QuitEventHandler implements Consumer<PlayerQuitEvent> {
    private UtoverseLibPlugin plugin;

    @Override
    public void accept(PlayerQuitEvent event) {
        try {
            UserProfile userProfile = (UserProfile) UserProfileRepo.getProfile(event.getPlayer().getName()).clone();
            userProfile.getUserProfileTimestamps().setLogout(new Date().getTime());
            cn.utoverse.utoverselib.profile.UserProfile.update(userProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
