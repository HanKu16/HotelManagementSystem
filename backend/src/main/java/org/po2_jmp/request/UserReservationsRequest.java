package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class UserReservationsRequest extends Request {

    private final String userId;

    public UserReservationsRequest(
            @JsonProperty("command") String command,
            @JsonProperty("userId") String userId) {
        super(command);
        this.userId = userId;
    }

}
