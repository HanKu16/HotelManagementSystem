package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegistrationResponse extends Response {

    private final String userId;

    public UserRegistrationResponse(ResponseStatus status, String message) {
        this(status, message, null);
    }

    public UserRegistrationResponse(ResponseStatus status,
            String message, String userId) {
        super(status, message);
        this.userId = userId;
    }

}
