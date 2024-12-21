package org.po2_jmp.service.helper;

import org.po2_jmp.entity.HotelRoom;
import org.po2_jmp.repository.contract.ReservationsRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AvailableRoomFinder {

    private final ReservationsRepository reservationsRepository;

    public AvailableRoomFinder(ReservationsRepository reservationsRepository) {
        if (reservationsRepository == null) {
            throw new IllegalArgumentException("ReservationsRepository can not be " +
                    "null, but null was passed to AvailableRoomFinder");
        }
        this.reservationsRepository = reservationsRepository;
    }

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
