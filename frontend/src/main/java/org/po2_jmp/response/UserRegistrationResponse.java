package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegistrationResponse extends Response {

    private final String userId;

    // Annotated constructor for JSON deserialization
    @JsonCreator
    public UserRegistrationResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("userId") String userId) {
        super(status, message);
        this.userId = userId;
    }
}
