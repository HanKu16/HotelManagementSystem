package org.po2_jmp.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class UserAuthenticationRequest extends Request {

    private final String userId;
    private final String password;

    public UserAuthenticationRequest(String type, String endpoint,
            String userId, String password) {
        super(type, endpoint);
        this.userId = userId;
        this.password = password;
    }

}
