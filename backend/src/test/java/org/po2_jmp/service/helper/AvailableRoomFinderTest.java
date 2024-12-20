package org.po2_jmp.service.helper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.po2_jmp.domain.RoomGuestCapacity;
import org.po2_jmp.domain.UserId;
import org.po2_jmp.entity.HotelRoom;
import org.po2_jmp.entity.Reservation;
import org.po2_jmp.repository.contract.ReservationsRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AvailableRoomFinderTest {

    @Mock
    private ReservationsRepository reservationsRepository;

    @InjectMocks
    private AvailableRoomFinder finder;

    @Test
    void Find_ShouldReturnEmptyOptional_WhenListOfRoomsIsEmpty() {
        LocalDate date = LocalDate.of(2099, 1, 1);
        List<HotelRoom> rooms = List.of();

        Optional<HotelRoom> optionalRoom = finder.find(rooms, date);

        assertTrue(optionalRoom.isEmpty());
    }

    @Test
    void Find_ShouldReturnEmptyOptional_WhenAllRoomsAreNotAvailable() {
        int hotelId = 2;
        RoomGuestCapacity guestCapacity = new RoomGuestCapacity(3);
        LocalDate date = LocalDate.of(2099, 1, 1);
        HotelRoom room1 = new HotelRoom(1, guestCapacity, hotelId);
        HotelRoom room2 = new HotelRoom(2, guestCapacity, hotelId);
        List<HotelRoom> rooms = List.of(room1, room2);
        when(reservationsRepository.findByRoomIdAndReservationDate(1, date))
                .thenReturn(Optional.of(createReservationForMock(date, 1)));
        when(reservationsRepository.findByRoomIdAndReservationDate(2, date))
                .thenReturn(Optional.of(createReservationForMock(date, 2)));

        Optional<HotelRoom> optionalRoom = finder.find(rooms, date);

        assertTrue(optionalRoom.isEmpty());
    }

    @Test
    void Find_ShouldReturnPresentOptional_WhenFirstRoomOnTheListIsAvailable() {
        int hotelId = 2;
        RoomGuestCapacity guestCapacity = new RoomGuestCapacity(3);
        LocalDate date = LocalDate.of(2099, 1, 1);
        HotelRoom room1 = new HotelRoom(1, guestCapacity, hotelId);
        HotelRoom room2 = new HotelRoom(2, guestCapacity, hotelId);
        List<HotelRoom> rooms = List.of(room1, room2);
        when(reservationsRepository.findByRoomIdAndReservationDate(1, date))
                .thenReturn(Optional.empty());

        Optional<HotelRoom> optionalRoom = finder.find(rooms, date);

        assertTrue(optionalRoom.isPresent());
    }

    @Test
    void Find_ShouldReturnPresentOptional_WhenSecondRoomOnTheListIsAvailable() {
        int hotelId = 2;
        RoomGuestCapacity guestCapacity = new RoomGuestCapacity(3);
        LocalDate date = LocalDate.of(2099, 1, 1);
        HotelRoom room1 = new HotelRoom(1, guestCapacity, hotelId);
        HotelRoom room2 = new HotelRoom(2, guestCapacity, hotelId);
        List<HotelRoom> rooms = List.of(room1, room2);
        Reservation existingReservation = createReservationForMock(date, 1);
        when(reservationsRepository.findByRoomIdAndReservationDate(1, date))
                .thenReturn(Optional.of(existingReservation));
        when(reservationsRepository.findByRoomIdAndReservationDate(2, date))
                .thenReturn(Optional.empty());

        Optional<HotelRoom> optionalRoom = finder.find(rooms, date);

        assertTrue(optionalRoom.isPresent());
    }

    private Reservation createReservationForMock(
            LocalDate reservationDate, int roomId) {
        int reservationId = 5;
        LocalDateTime creationDateTime = LocalDateTime.of(2024, 12, 20, 14, 30, 0);
        UserId userId = new UserId("szpaku");
        return new Reservation(
                reservationId,
                reservationDate,
                creationDateTime,
                userId,
                roomId);
    }

}
