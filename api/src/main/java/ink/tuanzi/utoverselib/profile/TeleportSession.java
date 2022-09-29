package cn.utoverse.utoverselib.profile.account;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Date;

@Builder
public class TeleportSession {
    /**
     * 发起请求的玩家
     */
    @Getter
    @Setter
    private Player requester;
    /**
     * 发起请求时所在位置
     */
    @Getter
    @Setter
    private Location location;
    /**
     * 发起时间
     */
    @Getter
    @Setter
    private Date createTime;
    /**
     * 请求原因
     */
    @Getter
    @Setter
    private TeleportReason reason;
}