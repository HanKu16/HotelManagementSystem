package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents the response for a user registration request.
 * <p>
 * This class extends the {@link Response} class and provides additional details
 * about the user registration result, including the user ID.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegistrationResponse extends Response {

    private final String userId;

    /**
     * Constructs a {@link UserRegistrationResponse} with a status and message.
     * The user ID is set to {@code null}.
     *
     * @param status The response status.
     * @param message The response message.
     */
    public UserRegistrationResponse(ResponseStatus status, String message) {
        this(status, message, null);
    }

    /**
     * Constructs a {@link UserRegistrationResponse} with a status, message,
     * and the user ID of the registered user.
     *
     * @param status The response status.
     * @param message The response message.
     * @param userId The user ID of the newly registered user.
     */
    public UserRegistrationResponse(ResponseStatus status,
            String message, String userId) {
        super(status, message);
        this.userId = userId;
    }

}
