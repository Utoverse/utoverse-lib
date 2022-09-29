package cn.utoverse.utoverselib.profile.account;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class AccountTimes {

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
