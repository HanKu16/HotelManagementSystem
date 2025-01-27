package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a request to authenticate a user.
 * <p>
 * This class extends the {@link Request} class and includes additional fields
 * required for user authentication, such as the {@code userId} and {@code password}.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class UserAuthenticationRequest extends Request {

    private final String userId;
    private final String password;

    /**
     * Constructs a {@code UserAuthenticationRequest} with the specified details.
     *
     * @param command the command associated with the request (inherited from {@link Request}).
     * @param userId the identifier of the user attempting to authenticate.
     * @param password the password of the user attempting to authenticate.
     */
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
