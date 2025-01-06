package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.po2_jmp.domain.RoleName;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthenticationResponse extends Response {

    private final String userId;
    private final RoleName role;

    @JsonCreator
    public UserAuthenticationResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("userId") String userId,
            @JsonProperty("role") RoleName role) {
        super(status, message);
        this.userId = userId;
        this.role = role;
    }
}
