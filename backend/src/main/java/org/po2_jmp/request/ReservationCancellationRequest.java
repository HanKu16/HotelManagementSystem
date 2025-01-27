package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a request to cancel a reservation.
 * <p>
 * This class extends the {@link Request} class and includes additional information
 * specific to reservation cancellation, such as the {@code reservationId}.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class ReservationCancellationRequest extends Request {

    private final int reservationId;

    /**
     * Constructs a {@code ReservationCancellationRequest} with the specified command and reservation ID.
     *
     * @param command the command associated with the request (inherited from {@link Request}).
     * @param reservationId the unique identifier of the reservation to be canceled.
     */
    @JsonCreator
    public ReservationCancellationRequest(
            @JsonProperty("command") String command,
            @JsonProperty("reservationId") int reservationId) {
        super(command);
        this.reservationId = reservationId;
    }

}
