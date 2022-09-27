package cn.utoverse.utoverselib.profile.account;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Builder
public class Account implements Cloneable {
    @Getter
    @Setter
    private UUID uuid;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private BigDecimal money;

    @Getter
    @Setter
    private ConcurrentHashMap<String, Location> homes = new ConcurrentHashMap<>();

    @Getter
    @Setter
    private boolean muted;

    @Getter
    @Setter
    private boolean jailed;

    @Getter
    @Setter
    private String ipAddress;

    @Getter
    @Setter
    private Location logoutLocation;

    @Getter
    @Setter
    private AccountTimes accountTimes;

    public Account clone() {
        try {
            return (Account) super.clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
    }
}
