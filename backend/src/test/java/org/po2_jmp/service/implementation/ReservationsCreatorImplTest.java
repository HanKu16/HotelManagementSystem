package org.po2_jmp.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.po2_jmp.domain.*;
import org.po2_jmp.entity.Address;
import org.po2_jmp.entity.Hotel;
import org.po2_jmp.entity.HotelRoom;
import org.po2_jmp.entity.Reservation;
import org.po2_jmp.repository.contract.HotelRoomsRepository;
import org.po2_jmp.repository.contract.HotelsRepository;
import org.po2_jmp.repository.contract.ReservationsRepository;
import org.po2_jmp.request.ReservationCreationRequest;
import org.po2_jmp.response.ReservationCreationResponse;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.service.helper.AvailableRoomFinder;
import org.po2_jmp.service.helper.ReservationCreationRequestValidator;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReservationsCreatorImplTest {

    @Mock
    private ReservationsRepository reservationsRepository;

    @Mock
    private HotelsRepository hotelsRepository;

    @Mock
    private HotelRoomsRepository hotelRoomsRepository;

    @Mock
    private AvailableRoomFinder availableRoomFinder;

    @Mock
    private ReservationCreationRequestValidator requestValidator;

    @InjectMocks
    private ReservationsCreatorImpl reservationsCreator;

    @Test
    void Create_ShouldReturnResponseOfStatusBadRequest_WhenRequestDataIsInvalid() {
        ReservationCreationRequest request = createRequestForMock();
        when(requestValidator.validate(request)).thenReturn(List.of("error"));

        ReservationCreationResponse response = reservationsCreator.create(request);

        assertEquals(ResponseStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void Create_ShouldReturnResponseOfStatusNotFound_WhenThereAreNoMatchingRooms() {
        ReservationCreationRequest request = createRequestForMock();
        Hotel hotel = createHotelForMock(request.getHotelId());
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(hotelsRepository.findById(request.getHotelId()))
                .thenReturn(Optional.of(hotel));
        when(hotelRoomsRepository.findAllByHotelIdAndGuestCapacity(
                request.getHotelId(), request.getGuestCapacity()))
                .thenReturn(List.of());

        ReservationCreationResponse response = reservationsCreator.create(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    void Create_ShouldReturnResponseOfStatusNotFound_WhenThereAreNoAvailableRooms() {
        ReservationCreationRequest request = createRequestForMock();
        Hotel hotel = createHotelForMock(request.getHotelId());
        RoomGuestCapacity guestCapacity = new RoomGuestCapacity(3);
        List<HotelRoom> matchingRooms = createRoomsForMock(guestCapacity);
        LocalDate date = LocalDate.of(2099, 1, 1);
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(hotelsRepository.findById(request.getHotelId()))
                .thenReturn(Optional.of(hotel));
        when(hotelRoomsRepository.findAllByHotelIdAndGuestCapacity(
                request.getHotelId(), request.getGuestCapacity()))
                .thenReturn(matchingRooms);
        when(availableRoomFinder.find(matchingRooms, date))
                .thenReturn(Optional.empty());

        ReservationCreationResponse response = reservationsCreator.create(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    void Create_ShouldReturnResponseOfStatusInternalServerError_WhenReservationWasNotSavedSuccessfully() {
        ReservationCreationRequest request = createRequestForMock();
        Hotel hotel = createHotelForMock(2);
        RoomGuestCapacity guestCapacity = new RoomGuestCapacity(3);
        List<HotelRoom> matchingRooms = createRoomsForMock(guestCapacity);
        when(requestValidator.validate(any(ReservationCreationRequest.class)))
                .thenReturn(List.of());
        when(hotelsRepository.findById(anyInt()))
                .thenReturn(Optional.of(hotel));
        when(hotelRoomsRepository.findAllByHotelIdAndGuestCapacity(anyInt(), anyInt()))
                .thenReturn(matchingRooms);
        HotelRoom room = matchingRooms.get(1);
        when(availableRoomFinder.find(anyList(), any(LocalDate.class)))
                .thenReturn(Optional.of(room));
        when(reservationsRepository.add(any(Reservation.class)))
                .thenReturn(Optional.empty());

        ReservationCreationResponse response = reservationsCreator.create(request);

        assertEquals(ResponseStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    }

    @Test
    void Create_ShouldReturnResponseOfStatusCreated_WhenReservationWasSavedSuccessfully() {
        ReservationCreationRequest request = createRequestForMock();
        Hotel hotel = createHotelForMock(2);
        RoomGuestCapacity guestCapacity = new RoomGuestCapacity(3);
        List<HotelRoom> matchingRooms = createRoomsForMock(guestCapacity);
        when(requestValidator.validate(any(ReservationCreationRequest.class)))
                .thenReturn(List.of());
        when(hotelsRepository.findById(anyInt()))
                .thenReturn(Optional.of(hotel));
        when(hotelRoomsRepository.findAllByHotelIdAndGuestCapacity(anyInt(), anyInt()))
                .thenReturn(matchingRooms);
        HotelRoom room = matchingRooms.get(1);
        when(availableRoomFinder.find(anyList(), any(LocalDate.class)))
                .thenReturn(Optional.of(room));
        when(reservationsRepository.add(any(Reservation.class)))
                .thenReturn(Optional.of(17));

        ReservationCreationResponse response = reservationsCreator.create(request);

        assertEquals(ResponseStatus.CREATED, response.getStatus());
    }

    private ReservationCreationRequest createRequestForMock() {
        return new ReservationCreationRequest("createReservation",
                "szpaku", LocalDate.of(2099, 1, 1), 2, 3);
    }

    private Hotel createHotelForMock(int hotelId) {
        return new Hotel(
                hotelId,
                new HotelName("Akropol"),
                new HotelDescription("To jest Akropol"),
                new Address(
                        new CityName("Warszawa"),
                        new StreetName("Mokra"),
                        new PostalCode("00-002"),
                        new BuildingNumber("2")
                ));
    }

    private List<HotelRoom> createRoomsForMock(
            RoomGuestCapacity guestCapacity) {
        int hotelId = 2;
        return List.of(
                new HotelRoom(5, guestCapacity, hotelId),
                new HotelRoom(16, guestCapacity, hotelId),
                new HotelRoom(31, guestCapacity, hotelId));
    }

}