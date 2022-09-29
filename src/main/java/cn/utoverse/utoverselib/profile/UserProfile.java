package cn.utoverse.utoverselib.profile;

import ink.tuanzi.utoverselib.profile.UserProfileTimestamps;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Objects;

public class UserProfile {

    public static void create(Player player, Long loginTime) {
        UserProfileTimestamps userProfileTimestamps = UserProfileTimestamps.builder()
                .login(loginTime)
                .jail(0L)
                .mute(0L)
                .lastTeleport(0L)
                .logout(0L)
                .build();

        ink.tuanzi.utoverselib.profile.UserProfile userProfile = ink.tuanzi.utoverselib.profile.UserProfile.builder()
                .name(player.getName())
                .uuid(player.getUniqueId())
                .money(BigDecimal.ZERO)
                .muted(false)
                .jailed(false)
                .ipAddress(Objects.requireNonNull(player.getAddress()).getAddress().toString())
                .userProfileTimestamps(userProfileTimestamps)
                .build();

        UserProfileRepo.createProfile(userProfile);
    }

    public static void update(ink.tuanzi.utoverselib.profile.UserProfile userProfile) {
        UserProfileRepo.saveProfile(userProfile);
    }
}
