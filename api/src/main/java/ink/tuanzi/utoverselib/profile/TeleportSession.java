package ink.tuanzi.utoverselib.profile;

import ink.tuanzi.utoverselib.constant.TeleportReason;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Date;

/**
 * 玩家档案中与传送会话相关的数据
 */
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