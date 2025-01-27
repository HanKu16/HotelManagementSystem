package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDate;

/**
 * Represents a request to create a new reservation.
 * <p>
 * This class extends the {@link Request} class and includes additional fields
 * required for creating a reservation, such as user information, reservation date,
 * hotel details, and guest capacity.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class ReservationCreationRequest extends Request {

    private final String userId;
    private final LocalDate reservationDate;
    private final int hotelId;
    private final int guestCapacity;

    /**
     * Constructs a {@code ReservationCreationRequest} with the specified details.
     *
     * @param command the command associated with the request (inherited from {@link Request}).
     * @param userId the identifier of the user making the reservation.
     * @param reservationDate the date for the reservation.
     * @param hotelId the unique identifier of the hotel where the reservation is being made.
     * @param guestCapacity the number of guests for the reservation.
     */
    @JsonCreator
    public ReservationCreationRequest(
            @JsonProperty("command") String command,
            @JsonProperty("userId") String userId,
            @JsonProperty("reservationDate") LocalDate reservationDate,
            @JsonProperty("hotelId") int hotelId,
            @JsonProperty("guestCapacity") int guestCapacity) {
        super(command);
        this.userId = userId;
        this.reservationDate = reservationDate;
        this.hotelId = hotelId;
        this.guestCapacity = guestCapacity;
    }

}
