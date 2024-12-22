package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.po2_jmp.domain.RoleName;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthenticationResponse extends Response {

    private final String userId;
    private final RoleName role;

    public UserAuthenticationResponse(ResponseStatus status, String message) {
        this(status, message, null, null);
    }

    public UserAuthenticationResponse(ResponseStatus status,
            String message, String userId, RoleName role) {
        super(status, message);
        this.userId = userId;
        this.role = role;
    }

}
