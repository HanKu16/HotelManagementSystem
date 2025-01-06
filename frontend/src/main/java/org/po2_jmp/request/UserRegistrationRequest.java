package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonCreator
    public UserRegistrationRequest(
            @JsonProperty("command") String command,
            @JsonProperty("userId") String userId,
            @JsonProperty("password") String password,
            @JsonProperty("confirmedPassword") String confirmedPassword) {
        super(command);
        this.userId = userId;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
    }

}
