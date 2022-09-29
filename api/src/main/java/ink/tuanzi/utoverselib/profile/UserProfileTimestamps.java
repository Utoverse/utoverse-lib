package ink.tuanzi.utoverselib.profile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 玩家档案中与时间相关的数据
 */
@Builder
public class UserProfileTimestamps {

    @Getter
    @Setter
    private Long lastTeleport;

    @Getter
    @Setter
    private Long mute;

    @Getter
    @Setter
    private Long jail;

    @Getter
    @Setter
    private Long logout;

    @Getter
    @Setter
    private Long login;

}
