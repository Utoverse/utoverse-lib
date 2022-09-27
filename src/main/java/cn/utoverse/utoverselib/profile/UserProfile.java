package cn.utoverse.utoverselib.profile;

import cn.utoverse.utoverselib.profile.account.Account;
import cn.utoverse.utoverselib.profile.account.AccountTimes;
import cn.utoverse.utoverselib.profile.account.UserProfileRepo;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class UserProfile {

    public static void create(Player player, Long loginTime) {
        AccountTimes accountTimes = AccountTimes.builder()
                .login(loginTime)
                .jail(0l)
                .mute(0l)
                .lastTeleport(0l)
                .logout(0l)
                .build();

        Account account = Account.builder()
                .name(player.getName())
                .uuid(player.getUniqueId())
                .money(BigDecimal.ZERO)
                .muted(false)
                .jailed(false)
                .ipAddress(player.getAddress().getAddress().toString())
                .accountTimes(accountTimes)
                .build();

        UserProfileRepo.createProfile(account);
    }

    public static void update(Account account) {
        UserProfileRepo.saveProfile(account);
    }
}
