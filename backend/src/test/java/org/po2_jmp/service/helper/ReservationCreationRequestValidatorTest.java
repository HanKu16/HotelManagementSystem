package org.po2_jmp.service.helper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.po2_jmp.domain.*;
import org.po2_jmp.entity.Address;
import org.po2_jmp.entity.Hotel;
import org.po2_jmp.entity.User;
import org.po2_jmp.repository.contract.HotelsRepository;
import org.po2_jmp.repository.contract.UsersRepository;
import org.po2_jmp.request.ReservationCreationRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationCreationRequestValidatorTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private HotelsRepository hotelsRepository;

    @InjectMocks
    private ReservationCreationRequestValidator requestValidator;

    @Test
    void Validate_ShouldReturnEmptyList_WhenRequestIsValid() {
        User user = createUserForMock();
        Hotel hotel = createHotelForMock();
        LocalDate dayFromTheFuture = LocalDate.of(3099, 1, 1);
        when(usersRepository.findById(user.getId().getValue()))
                .thenReturn(Optional.of(user));
        when(hotelsRepository.findById(hotel.getId()))
                .thenReturn(Optional.of(hotel));

        ReservationCreationRequest request = getRequest(
                user.getId().getValue(), hotel.getId(), dayFromTheFuture);
        List<String> errors = requestValidator.validate(request);

        assertTrue(errors.isEmpty());
    }

    @Test
    void Validate_ShouldReturnListOfSize1_WhenDateIsPast() {
        User user = createUserForMock();
        Hotel hotel = createHotelForMock();
        LocalDate dayFromThePast = LocalDate.of(2014, 12, 12);
        when(usersRepository.findById(user.getId().getValue()))
                .thenReturn(Optional.of(user));
        when(hotelsRepository.findById(hotel.getId()))
                .thenReturn(Optional.of(hotel));

        ReservationCreationRequest request = getRequest(
                user.getId().getValue(), hotel.getId(), dayFromThePast);
        List<String> errors = requestValidator.validate(request);

        assertEquals(1, errors.size());
    }

    @Test
    void Validate_ShouldReturnListOfSize1_WhenUserDoesNotExist() {
        String notExistingUserId = "franek15";
        Hotel hotel = createHotelForMock();
        LocalDate dayFromTheFuture = LocalDate.of(3099, 1, 1);
        when(usersRepository.findById(notExistingUserId))
                .thenReturn(Optional.empty());
        when(hotelsRepository.findById(hotel.getId()))
                .thenReturn(Optional.of(hotel));

        ReservationCreationRequest request = getRequest(
                notExistingUserId, hotel.getId(), dayFromTheFuture);
        List<String> errors = requestValidator.validate(request);

        assertEquals(1, errors.size());
    }

    @Test
    void Validate_ShouldReturnListOfSize1_WhenHotelDoesNotExist() {
        User user = createUserForMock();
        int notExistingHotelId = 7;
        LocalDate dayFromTheFuture = LocalDate.of(3099, 1, 1);
        when(usersRepository.findById(user.getId().getValue()))
                .thenReturn(Optional.of(user));
        when(hotelsRepository.findById(notExistingHotelId))
                .thenReturn(Optional.empty());

        ReservationCreationRequest request = getRequest(
                user.getId().getValue(), notExistingHotelId, dayFromTheFuture);
        List<String> errors = requestValidator.validate(request);

        assertEquals(1, errors.size());
    }

    @Test
    void Validate_ShouldReturnListOfSize2_WhenUserDoesNotExistAndDateIsPast() {
        String notExistingUserId = "franek15";
        Hotel hotel = createHotelForMock();
        LocalDate dayFromThePast = LocalDate.of(2014, 12, 12);
        when(usersRepository.findById(notExistingUserId))
                .thenReturn(Optional.empty());
        when(hotelsRepository.findById(hotel.getId()))
                .thenReturn(Optional.of(hotel));

        ReservationCreationRequest request = getRequest(
                notExistingUserId, hotel.getId(), dayFromThePast);
        List<String> errors = requestValidator.validate(request);

        assertEquals(2, errors.size());
    }

    @Test
    void Validate_ShouldReturnListOfSize2_WhenUserDoesNotExistAndHotelDoesNotExist() {
        String notExistingUserId = "franek15";
        int notExistingHotelId = 17;
        LocalDate dayFromTheFuture = LocalDate.of(3099, 12, 12);
        when(usersRepository.findById(notExistingUserId))
                .thenReturn(Optional.empty());
        when(hotelsRepository.findById(notExistingHotelId))
                .thenReturn(Optional.empty());

        ReservationCreationRequest request = getRequest(
                notExistingUserId, notExistingHotelId, dayFromTheFuture);
        List<String> errors = requestValidator.validate(request);

        assertEquals(2, errors.size());
    }

    @Test
    void Validate_ShouldReturnListOfSize2_WhenDateIsPastAndHotelDoesNotExist() {
        User user = createUserForMock();
        int notExistingHotelId = 17;
        LocalDate dayFromThePast = LocalDate.of(2014, 12, 12);
        when(usersRepository.findById(user.getId().getValue()))
                .thenReturn(Optional.of(user));
        when(hotelsRepository.findById(notExistingHotelId))
                .thenReturn(Optional.empty());

        ReservationCreationRequest request = getRequest(user.getId().getValue(),
                notExistingHotelId, dayFromThePast);
        List<String> errors = requestValidator.validate(request);

        assertEquals(2, errors.size());
    }

    @Test
    void Validate_ShouldReturnListOfSize3_WhenUDateIsPastAndHotelDoesNotExistAndUserDoesNotExist() {
        String notExistingUserId = "szpaku";
        int notExistingHotelId = 17;
        LocalDate dayFromThePast = LocalDate.of(2014, 12, 12);
        when(usersRepository.findById(notExistingUserId))
                .thenReturn(Optional.empty());
        when(hotelsRepository.findById(notExistingHotelId))
                .thenReturn(Optional.empty());

        ReservationCreationRequest request = getRequest(notExistingUserId,
                notExistingHotelId, dayFromThePast);
        List<String> errors = requestValidator.validate(request);

        assertEquals(3, errors.size());
    }

    private ReservationCreationRequest getRequest(String userId,
            int hotelId, LocalDate reservationDate) {
        String type = "GET";
        String endpoint = "/reservations";
        int guestCapacity = 3;
        return new ReservationCreationRequest(
                type, endpoint,
                userId, reservationDate,
                hotelId, guestCapacity);
    }

    private User createUserForMock() {
        return new User(
                new UserId("adam159"),
                new UserPassword("hasloadam"),
                LocalDateTime.of(2024, 5, 19, 14, 30, 0), 1);
    }

    private Hotel createHotelForMock() {
        return new Hotel(
                5,
                new HotelName("Akropol"),
                new HotelDescription("To jest Akropol"),
                new Address(
                        new CityName("Warszawa"),
                        new PostalCode("00-002"),
                        new BuildingNumber("2")
                ));
    }

}
