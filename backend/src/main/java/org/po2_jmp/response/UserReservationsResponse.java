package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.List;


/**
 * Represents the response for a user's reservation request.
 * <p>
 * This class extends the {@link Response} class and includes the user's ID
 * and a list of reservations associated with that user.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserReservationsResponse extends Response {

    private final String userId;
    private final List<ReservationDto> reservations;

    /**
     * Constructs a {@link UserReservationsResponse} with a status and message.
     * The user ID and reservations list are set to {@code null}.
     *
     * @param status The response status.
     * @param message The response message.
     */
    public UserReservationsResponse(ResponseStatus status, String message) {
        this(status, message, null, null);
    }

    /**
     * Constructs a {@link UserReservationsResponse} with a status, message,
     * the user ID, and a list of reservations.
     *
     * @param status The response status.
     * @param message The response message.
     * @param userId The ID of the user.
     * @param reservations The list of reservations associated with the user.
     */
    public UserReservationsResponse(ResponseStatus status, String message,
            String userId, List<ReservationDto> reservations) {
        super(status, message);
        this.userId = userId;
        this.reservations = reservations;
    }

}
