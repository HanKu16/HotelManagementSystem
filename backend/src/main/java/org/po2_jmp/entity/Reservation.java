package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.UserId;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a reservation entity.
 * <p>
 * The {@link Reservation} class encapsulates the details of a reservation,
 * including the reservation date, the creation date and time, the user
 * making the reservation, and the room being reserved. It ensures that the
 * reservation date, creation date time, and user ID are not null when
 * creating a reservation instance.
 */
@Getter
public class Reservation {

    private final int id;
    private final LocalDate reservationDate;
    private final LocalDateTime creationDateTime;
    private final UserId userId;
    private final int roomId;

    /**
     * Constructs a {@link Reservation} with the specified reservation date,
     * creation date time, user ID, and room ID.
     * <p>
     * This constructor assigns a default id of 0 to the reservation and
     * throws an {@link IllegalArgumentException} if any of the reservation
     * date, creation date time, or user ID is null.
     *
     * @param reservationDate the date on which the reservation was made
     * @param creationDateTime the date and time when the reservation was created
     * @param userId the user ID of the person making the reservation
     * @param roomId the identifier of the reserved hotel room
     *
     * @throws IllegalArgumentException if any of the reservation date,
     * creation date time, or user ID is null
     */
    public Reservation(LocalDate reservationDate,
                       LocalDateTime creationDateTime,
                       UserId userId, int roomId) {
        this(0, reservationDate, creationDateTime, userId, roomId);
    }

    /**
     * Constructs a {@link Reservation} with the specified id, reservation date,
     * creation date time, user ID, and room ID.
     * <p>
     * This constructor allows for the creation of a reservation with a
     * specific id and throws an {@link IllegalArgumentException}
     * if any of the reservation date, creation date time, or user ID is null.
     *
     * @param id the unique identifier for the reservation
     * @param reservationDate the date on which the reservation was made
     * @param creationDateTime the date and time when the reservation was created
     * @param userId the user ID of the person making the reservation
     * @param roomId the identifier of the reserved hotel room
     *
     * @throws IllegalArgumentException if any of the reservation date,
     * creation date time, or user ID is null
     */
    public Reservation(int id, LocalDate reservationDate,
                       LocalDateTime creationDateTime, UserId userId, int roomId) {
        if (areAnyNullParams(reservationDate, creationDateTime, userId)) {
            throw new IllegalArgumentException("Reservation date and " +
                    "creation date time can not be nulls, but null was " +
                    "passed to Reservation constructor");
        }
        this.id = id;
        this.reservationDate = reservationDate;
        this.creationDateTime = creationDateTime;
        this.userId = userId;
        this.roomId = roomId;
    }

    public boolean areAnyNullParams(LocalDate reservationDate,
            LocalDateTime creationDateTime, UserId userId) {
        return (reservationDate == null) || (creationDateTime == null) ||
                (userId == null);
    }

}
