package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.po2_jmp.domain.RoleName;
import org.po2_jmp.entity.Role;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthenticationResponse extends Response {

    private final RoleName role;

    public UserAuthenticationResponse(ResponseStatus status,
            String message, RoleName role) {
        super(status, message);
        this.role = role;
    }

}
