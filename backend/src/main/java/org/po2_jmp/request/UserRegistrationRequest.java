package org.po2_jmp.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class UserRegistrationRequest extends Request {

    private final String userId;
    private final String password;
    private final String confirmedPassword;

    public UserRegistrationRequest(String type, String endpoint,
            String userId, String password, String confirmedPassword) {
        super(type, endpoint);
        this.userId = userId;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
    }

}
