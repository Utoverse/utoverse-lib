package cn.utoverse.utoverselib.profile.listener.handler;

import cn.utoverse.utoverselib.AbstractUtoverseLibPlugin;
import cn.utoverse.utoverselib.profile.UserProfile;
import cn.utoverse.utoverselib.profile.account.Account;
import cn.utoverse.utoverselib.profile.account.UserProfileRepo;
import lombok.AllArgsConstructor;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.function.Consumer;

@AllArgsConstructor
public class QuitEventHandler implements Consumer<PlayerQuitEvent> {
    private AbstractUtoverseLibPlugin plugin;

    @Override
    public void accept(PlayerQuitEvent event) {
        try {
            Account account = UserProfileRepo.getProfile(event.getPlayer().getName()).clone();
            account.getAccountTimes().setLogout(new Date().getTime());
            UserProfile.update(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
