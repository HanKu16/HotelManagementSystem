package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a request to retrieve a user's reservations.
 * <p>
 * This class extends the {@link Request} class and includes an additional field,
 * {@code userId}, which specifies the user for whom the reservations are being requested.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class UserReservationsRequest extends Request {

    private final String userId;

    /**
     * Constructs a {@code UserReservationsRequest} with the specified command and user ID.
     *
     * @param command the command associated with the request (inherited from {@link Request}).
     * @param userId the unique identifier of the user whose reservations are being requested.
     */
    public UserReservationsRequest(
            @JsonProperty("command") String command,
            @JsonProperty("userId") String userId) {
        super(command);
        this.userId = userId;
    }

}
