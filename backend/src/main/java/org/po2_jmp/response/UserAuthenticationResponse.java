package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.po2_jmp.domain.RoleName;

/**
 * Represents the response for a user authentication request.
 * <p>
 * This class extends the {@link Response} class and provides details
 * about the user authentication result, including the user's ID and
 * their role in the system.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthenticationResponse extends Response {

    private final String userId;
    private final RoleName role;

    /**
     * Constructs a {@link UserAuthenticationResponse} with a status and message.
     * The user ID and role are set to {@code null}.
     *
     * @param status The response status.
     * @param message The response message.
     */
    public UserAuthenticationResponse(ResponseStatus status, String message) {
        this(status, message, null, null);
    }

    /**
     * Constructs a {@link UserAuthenticationResponse} with a status, message,
     * user ID and role.
     *
     * @param status The response status.
     * @param message The response message.
     * @param userId The user ID of the authenticated user.
     * @param role The role of the authenticated user.
     */
    public UserAuthenticationResponse(ResponseStatus status,
            String message, String userId, RoleName role) {
        super(status, message);
        this.userId = userId;
        this.role = role;
    }

}
