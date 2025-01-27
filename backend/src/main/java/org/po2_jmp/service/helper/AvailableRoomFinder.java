package org.po2_jmp.service.helper;

import org.po2_jmp.entity.HotelRoom;
import org.po2_jmp.repository.contract.ReservationsRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The {@code AvailableRoomFinder} class is responsible for finding an available hotel room
 * that is not reserved on a given date from a list of rooms.
 *
 * <p>This class interacts with a {@link ReservationsRepository} to check for room availability
 * and ensures that the chosen room is not already reserved for the specified date.</p>
 */
public class AvailableRoomFinder {

    private final ReservationsRepository reservationsRepository;

    /**
     * Constructs a new {@code AvailableRoomFinder} with the specified {@link ReservationsRepository}.
     *
     * @param reservationsRepository The {@link ReservationsRepository} used to check room reservations.
     * @throws IllegalArgumentException if the provided {@link ReservationsRepository} is {@code null}.
     */
    public AvailableRoomFinder(ReservationsRepository reservationsRepository) {
        if (reservationsRepository == null) {
            throw new IllegalArgumentException("ReservationsRepository can not be " +
                    "null, but null was passed to AvailableRoomFinder");
        }
        this.reservationsRepository = reservationsRepository;
    }

    /**
     * Finds the first available room from the provided list of rooms that is not reserved
     * on the specified date.
     *
     * @param rooms The list of {@link HotelRoom} objects to check for availability.
     * @param reservationDate The date for which room availability is being checked.
     * @return An {@link Optional} containing the first available {@link HotelRoom},
     *        or an empty {@link Optional} if no room is available.
     */
    public Optional<HotelRoom> find(List<HotelRoom> rooms, LocalDate reservationDate) {
        return rooms.stream()
                .filter(room -> !isHotelRoomReservedOn(room.getId(), reservationDate))
                .findFirst();
    }

    private boolean isHotelRoomReservedOn(int hotelRoomId, LocalDate date) {
        return reservationsRepository
                .findByRoomIdAndReservationDate(hotelRoomId, date)
                .isPresent();
    }

}
