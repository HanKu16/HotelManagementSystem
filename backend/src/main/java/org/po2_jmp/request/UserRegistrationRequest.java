package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a request to register a new user.
 * <p>
 * This class extends the {@link Request} class and includes additional fields
 * required for user registration, such as the {@code userId}, {@code password},
 * and {@code confirmedPassword}.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class UserRegistrationRequest extends Request {

    private final String userId;
    private final String password;
    private final String confirmedPassword;

    /**
     * Constructs a {@code UserRegistrationRequest} with the specified details.
     *
     * @param command the command associated with the request (inherited from {@link Request}).
     * @param userId the unique identifier for the user to be registered.
     * @param password the password chosen by the user.
     * @param confirmedPassword the confirmation of the user's password.
     */
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
