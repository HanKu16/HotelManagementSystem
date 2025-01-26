package org.po2_jmp.repository.contract;

import org.po2_jmp.entity.Reservation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Represents a repository interface for managing {@link Reservation} entities.
 * <p>
 * The {@link ReservationsRepository} interface defines the contract for accessing and
 * modifying reservation information in a data storage system. This interface provides methods
 * to retrieve reservations by different criteria, add new reservations, and delete reservations
 * from the system.
 * </p>
 */
public interface ReservationsRepository {

    /**
     * Finds a reservation by its unique identifier.
     *
     * @param id the unique identifier of the reservation
     * @return an {@link Optional} containing the {@link Reservation} if found, or an empty
     *         {@link Optional} if no reservation with the given id exists
     */
    Optional<Reservation> findById(int id);

    /**
     * Finds all reservations for a given user identified by their user ID.
     *
     * @param userId the user identifier to search for
     * @return a {@link List} of {@link Reservation} objects associated with the given user ID
     */
    List<Reservation> findAllByUserId(String userId);

    /**
     * Finds a reservation by room ID and reservation date.
     *
     * @param roomId the unique identifier of the room
     * @param reservationDate the reservation date
     * @return an {@link Optional} containing the {@link Reservation} if found, or an empty
     *         {@link Optional} if no reservation exists for the given room and date
     */
    Optional<Reservation> findByRoomIdAndReservationDate(
            int roomId, LocalDate reservationDate);

    /**
     * Adds a new reservation to the system.
     *
     * @param reservation the {@link Reservation} to be added
     * @return an {@link Optional} containing the ID of the newly added reservation if the addition
     *         is successful, or an empty {@link Optional} if the reservation could not be added
     */
    Optional<Integer> add(Reservation reservation);

    /**
     * Deletes a reservation by its unique identifier.
     *
     * @param id the unique identifier of the reservation to be deleted
     * @return the number of rows affected
     */
    int deleteById(int id);

}
