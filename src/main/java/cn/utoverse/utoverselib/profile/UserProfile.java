package cn.utoverse.utoverselib.profile;

import cn.utoverse.utoverselib.AbstractUtoverseLibPlugin;
import me.lucko.helper.profiles.Profile;
import me.lucko.helper.profiles.ProfileRepository;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class UserProfile  {


    /**
     * 获取用户信息库
     *
     * @return
     */
    public static ProfileRepository getProfileRepo() {
        return AbstractUtoverseLibPlugin.getInstance().getService(ProfileRepository.class);
    }

    /**
     * 获取用户信息
     *
     * @param uuid UUID
     * @return 用户信息
     */
    public static Profile getUserProfile(@NotNull UUID uuid) {
        return getUserProfile(Bukkit.getOfflinePlayer(uuid));
    }

    /**
     * 获取用户信息
     *
     * @param offlinePlayer 离线玩家
     * @param <T>           继承自OfflinePlayer的对象
     * @return 用户信息
     */
    public static  <T extends OfflinePlayer> Profile getUserProfile(T offlinePlayer) {
        return getUserProfile(offlinePlayer.getName());
    }

    /**
     * 获取用户信息，用户信息不存在时返回null
     *
     * @param playerName 玩家名
     * @return 用户信息
     */
    @Nullable
    public static Profile getUserProfile(@NotNull String playerName) {
        ProfileRepository repo = getProfileRepo();
        Optional<Profile> lucksProfile = repo.getProfile(playerName);
        if (lucksProfile.isPresent()) {
            return lucksProfile.get();
        }
        return null;
    }
}
