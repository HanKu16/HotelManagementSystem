package org.po2_jmp.service.implementation;

import org.po2_jmp.entity.Hotel;
import org.po2_jmp.entity.HotelRoom;
import org.po2_jmp.entity.Reservation;
import org.po2_jmp.repository.contract.HotelRoomsRepository;
import org.po2_jmp.repository.contract.HotelsRepository;
import org.po2_jmp.repository.contract.ReservationsRepository;
import org.po2_jmp.request.UserReservationsRequest;
import org.po2_jmp.response.ReservationDto;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.response.UserReservationsResponse;
import org.po2_jmp.service.contract.ReservationsProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The {@code ReservationsProviderImpl} class implements the {@link ReservationsProvider} interface.
 * It provides functionality for retrieving all reservations made by a user, returning the details
 * of each reservation in the form of {@link ReservationDto} objects.
 */
public class ReservationsProviderImpl implements ReservationsProvider {

    private final ReservationsRepository reservationsRepository;
    private final HotelRoomsRepository hotelRoomsRepository;
    private final HotelsRepository hotelsRepository;

    /**
     * Constructs a new {@code ReservationsProviderImpl} instance with the specified repositories.
     *
     * @param reservationsRepository The repository used for fetching reservations.
     * @param hotelRoomsRepository The repository used for fetching hotel room details.
     * @param hotelsRepository The repository used for fetching hotel details.
     */
    public ReservationsProviderImpl(
            ReservationsRepository reservationsRepository,
            HotelRoomsRepository hotelRoomsRepository,
            HotelsRepository hotelsRepository) {
        this.reservationsRepository = reservationsRepository;
        this.hotelRoomsRepository = hotelRoomsRepository;
        this.hotelsRepository = hotelsRepository;
    }

    /**
     * Retrieves the list of reservations for a specified user.
     *
     * @param request The {@link UserReservationsRequest} containing the user
     *        ID for which reservations are requested.
     * @return A {@link UserReservationsResponse} containing a list of
     *        {@link ReservationDto} objects representing reservation.
     */
    @Override
    public UserReservationsResponse getUserReservations(UserReservationsRequest request) {
        List<Reservation> userReservations = reservationsRepository
                .findAllByUserId(request.getUserId());
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation reservation : userReservations) {
            ReservationDto dto = createReservationDto(reservation);
            reservationDtos.add(dto);
        }
        return new UserReservationsResponse(ResponseStatus.OK,
                "", request.getUserId(), reservationDtos);
    }

    private ReservationDto createReservationDto(Reservation reservation) {
        Optional<HotelRoom> optionalRoom = hotelRoomsRepository
                .findById(reservation.getRoomId());
        String hotelName = null;
        Integer guestCapacity = null;
        if (optionalRoom.isPresent()) {
            Optional<Hotel> optionalHotel = hotelsRepository
                    .findById(optionalRoom.get().getHotelId());
            if (optionalHotel.isPresent()) {
                hotelName = optionalHotel.get().getName().getValue();
                guestCapacity = optionalRoom.get().getGuestCapacity().getValue();
            }
        }
        return new ReservationDto(reservation.getId(),
                reservation.getReservationDate(),
                reservation.getCreationDateTime(),
                hotelName, guestCapacity);
    }

}