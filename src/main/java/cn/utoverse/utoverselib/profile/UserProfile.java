package cn.utoverse.utoverselib.profile;

import cn.utoverse.utoverselib.profile.account.Account;
import cn.utoverse.utoverselib.profile.account.AccountTimes;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Objects;

public class UserProfile {

    public static void create(Player player, Long loginTime) {
        AccountTimes accountTimes = AccountTimes.builder()
                .login(loginTime)
                .jail(0L)
                .mute(0L)
                .lastTeleport(0L)
                .logout(0L)
                .build();

        Account account = Account.builder()
                .name(player.getName())
                .uuid(player.getUniqueId())
                .money(BigDecimal.ZERO)
                .muted(false)
                .jailed(false)
                .ipAddress(Objects.requireNonNull(player.getAddress()).getAddress().toString())
                .accountTimes(accountTimes)
                .build();

        UserProfileRepo.createProfile(account);
    }

    public static void update(Account account) {
        UserProfileRepo.saveProfile(account);
    }
}
