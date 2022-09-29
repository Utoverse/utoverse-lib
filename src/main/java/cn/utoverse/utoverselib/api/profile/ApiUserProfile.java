package cn.utoverse.utoverselib.api.profile;

import ink.tuanzi.utoverselib.profile.UserProfile;
import ink.tuanzi.utoverselib.profile.UserProfileRepo;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class ApiUserProfile implements UserProfileRepo {

    @Override
    public <T extends OfflinePlayer> UserProfile getProfile(T player) {
        return cn.utoverse.utoverselib.profile.UserProfileRepo.getProfile(player);
    }

    @Override
    public UserProfile getProfile(String playerName) {
        return cn.utoverse.utoverselib.profile.UserProfileRepo.getProfile(playerName);
    }

    @Override
    public void loadUserNameMapAsync() {
        cn.utoverse.utoverselib.profile.UserProfileRepo.loadUserNameMapAsync();
    }

    @Override
    public List<String> getUserNameMap() {
        return cn.utoverse.utoverselib.profile.UserProfileRepo.getUsernameMap();
    }
}
