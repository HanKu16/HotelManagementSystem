package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents the response containing information about a reservation cancellation.
 * <p>
 * This class extends {@link Response} and includes the reservation ID in the response,
 * which is relevant when a reservation has been successfully cancelled.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationCancellationResponse extends Response {

    private final Integer reservationId;

    /**
     * Constructs a {@code ReservationCancellationResponse} with the specified status and message,
     * but without a reservation ID. Should be used for error responses).
     *
     * @param status the status of the response.
     * @param message the message associated with the response.
     */
    public ReservationCancellationResponse(
            ResponseStatus status, String message) {
        this(status, message, null);
    }

    /**
     * Constructs a {@code ReservationCancellationResponse} with the specified status, message,
     * and the reservation ID of the cancelled reservation.
     *
     * @param status the status of the response.
     * @param message the message associated with the response.
     * @param reservationId the unique identifier of the cancelled reservation.
     */
    public ReservationCancellationResponse(ResponseStatus status,
            String message, Integer reservationId) {
        super(status, message);
        this.reservationId = reservationId;
    }

}
