package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class UserAuthenticationRequest extends Request {

    private final String userId;
    private final String password;

    @JsonCreator
    public UserAuthenticationRequest(
            @JsonProperty("command") String command,
            @JsonProperty("userId") String userId,
            @JsonProperty("password") String password) {
        super(command);
        this.userId = userId;
        this.password = password;
    }

}
