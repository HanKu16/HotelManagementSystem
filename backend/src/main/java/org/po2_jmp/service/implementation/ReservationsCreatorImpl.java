package org.po2_jmp.service.implementation;

import org.po2_jmp.domain.UserId;
import org.po2_jmp.entity.Hotel;
import org.po2_jmp.entity.HotelRoom;
import org.po2_jmp.entity.Reservation;
import org.po2_jmp.repository.contract.HotelRoomsRepository;
import org.po2_jmp.repository.contract.HotelsRepository;
import org.po2_jmp.repository.contract.ReservationsRepository;
import org.po2_jmp.request.ReservationCreationRequest;
import org.po2_jmp.response.ReservationCreationResponse;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.service.contract.ReservationsCreator;
import org.po2_jmp.service.helper.AvailableRoomFinder;
import org.po2_jmp.service.helper.ReservationCreationRequestValidator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The {@code ReservationsCreatorImpl} class implements the {@link ReservationsCreator} interface.
 * It handles the creation of reservations for a given hotel and date, ensuring that all
 * validation checks are met. This includes checking room availability, user existence,
 * and ensuring that the reservation can be created successfully.
 */
public class ReservationsCreatorImpl implements ReservationsCreator {

    private final ReservationsRepository reservationsRepository;
    private final HotelsRepository hotelsRepository;
    private final HotelRoomsRepository hotelRoomsRepository;
    private final AvailableRoomFinder availableRoomFinder;
    private final ReservationCreationRequestValidator requestValidator;

    /**
     * Constructs a new {@code ReservationsCreatorImpl} instance with the specified repositories
     * and helper classes. It validates the constructor parameters to ensure they are non-null.
     *
     * @param reservationsRepository The repository used for saving reservations.
     * @param hotelsRepository The repository used for fetching hotel details.
     * @param hotelRoomsRepository The repository used for fetching hotel rooms.
     * @param availableRoomFinder The helper to find available rooms based on reservation date.
     * @param requestValidator The validator for checking request validity.
     * @throws IllegalArgumentException if any of the provided parameters is {@code null}.
     */
    public ReservationsCreatorImpl(
            ReservationsRepository reservationsRepository,
            HotelsRepository hotelsRepository,
            HotelRoomsRepository hotelRoomsRepository,
            AvailableRoomFinder availableRoomFinder,
            ReservationCreationRequestValidator requestValidator) {
        validateConstructorParams(reservationsRepository, hotelsRepository,
                hotelRoomsRepository, availableRoomFinder, requestValidator);
        this.reservationsRepository = reservationsRepository;
        this.hotelsRepository = hotelsRepository;
        this.hotelRoomsRepository = hotelRoomsRepository;
        this.availableRoomFinder = availableRoomFinder;
        this.requestValidator = requestValidator;
    }

    /**
     * Creates a reservation based on the provided {@link ReservationCreationRequest}.
     * It first validates the request, then checks for room availability and if successful,
     * it creates the reservation.
     *
     * @param request The {@link ReservationCreationRequest} containing details about the reservation.
     * @return A {@link ReservationCreationResponse} indicating the result of the reservation creation.
     */
    @Override
    public ReservationCreationResponse create(ReservationCreationRequest request) {
        List<String> errorMessages = requestValidator.validate(request);
        if (!errorMessages.isEmpty()) {
            return new ReservationCreationResponse(ResponseStatus.BAD_REQUEST,
                    String.join(", ", errorMessages));
        }
        Hotel hotel = hotelsRepository.findById(request.getHotelId()).get();
        List<HotelRoom> matchingRooms = getMatchingRooms(request);
        if (matchingRooms.isEmpty()) {
            return new ReservationCreationResponse(ResponseStatus.NOT_FOUND,
                    "there is no matching room in this hotel");
        }
        Optional<HotelRoom> optionalAvailableRoom = availableRoomFinder
                .find(matchingRooms, request.getReservationDate());
        if (optionalAvailableRoom.isEmpty()) {
            return new ReservationCreationResponse(ResponseStatus.NOT_FOUND,
                    "there is no available rooms for this day");
        }
        return saveReservationAndCreateResponse(optionalAvailableRoom.get().getId(),
                hotel.getName().getValue(), request);
    }

    private ReservationCreationResponse saveReservationAndCreateResponse(int hotelRoomId,
            String hotelName, ReservationCreationRequest request) {
        Reservation reservation = createNewReservation(hotelRoomId, request);
        Optional<Integer> optionalReservationId =
                reservationsRepository.add(reservation);
        return optionalReservationId.isPresent() ?
                new ReservationCreationResponse(ResponseStatus.CREATED,
                        "reservation created", hotelName, request.getReservationDate()):
                new ReservationCreationResponse(ResponseStatus.INTERNAL_SERVER_ERROR,
                        "failed to create reservation, please try again");
    }

    private List<HotelRoom> getMatchingRooms(ReservationCreationRequest request) {
        return hotelRoomsRepository.findAllByHotelIdAndGuestCapacity(
                request.getHotelId(), request.getGuestCapacity());
    }

    private Reservation createNewReservation(int hotelRoomId,
            ReservationCreationRequest request) {
        return new Reservation(request.getReservationDate(), LocalDateTime.now(),
                new UserId(request.getUserId()), hotelRoomId);
    }

    private void validateConstructorParams(
            ReservationsRepository reservationsRepository,
            HotelsRepository hotelsRepository,
            HotelRoomsRepository hotelRoomsRepository,
            AvailableRoomFinder availableRoomFinder,
            ReservationCreationRequestValidator requestValidator) {
        if (reservationsRepository == null) {
            throw new IllegalArgumentException("ReservationRepository can not be null," +
                    " but null was passed to ReservationsCreatorImpl");
        }
        if (hotelsRepository == null) {
            throw new IllegalArgumentException("HotelsRepository can not be null," +
                    " but null was passed to ReservationsCreatorImpl");
        }
        if (hotelRoomsRepository == null) {
            throw new IllegalArgumentException("HotelRoomsRepository can not be null," +
                    " but null was passed to ReservationsCreatorImpl");
        }
        if (availableRoomFinder == null) {
            throw new IllegalArgumentException("AvailableRoomFinder can not be null," +
                    " but null was passed to ReservationsCreatorImpl");
        }
        if (requestValidator == null) {
            throw new IllegalArgumentException("ReservationCreationRequestValidator " +
                    "can not be null, but null was passed to ReservationsCreatorImpl");
        }
    }

}
