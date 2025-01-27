package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDate;

/**
 * Represents the response containing details about a reservation creation.
 * <p>
 * This class extends {@link Response} and includes the name of the hotel and the reservation
 * date for the created reservation.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationCreationResponse extends Response {

    private final String hotelName;
    private final LocalDate reservationDate;

    /**
     * Constructs a {@code ReservationCreationResponse} with the specified status and message,
     * but without the hotel name and reservation date. Should be used for error responses).
     *
     * @param status the status of the response.
     * @param message the message associated with the response.
     */
    public ReservationCreationResponse(ResponseStatus status, String message) {
        super(status, message);
        this.hotelName = null;
        this.reservationDate = null;
    }

    /**
     * Constructs a {@code ReservationCreationResponse} with the specified status, message,
     * hotel name, and reservation date.
     *
     * @param status the status of the response.
     * @param message the message associated with the response.
     * @param hotelName the name of the hotel where the reservation was made.
     * @param reservationDate the date the reservation was created.
     */
    public ReservationCreationResponse(ResponseStatus status, String message,
            String hotelName, LocalDate reservationDate) {
        super(status, message);
        this.hotelName = hotelName;
        this.reservationDate = reservationDate;
    }

}
