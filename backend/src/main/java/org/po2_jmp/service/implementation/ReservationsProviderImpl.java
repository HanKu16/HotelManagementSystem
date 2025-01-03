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

public class ReservationsProviderImpl implements ReservationsProvider {

    private final ReservationsRepository reservationsRepository;
    private final HotelRoomsRepository hotelRoomsRepository;
    private final HotelsRepository hotelsRepository;

    public ReservationsProviderImpl(
            ReservationsRepository reservationsRepository,
            HotelRoomsRepository hotelRoomsRepository,
            HotelsRepository hotelsRepository) {
        this.reservationsRepository = reservationsRepository;
        this.hotelRoomsRepository = hotelRoomsRepository;
        this.hotelsRepository = hotelsRepository;
    }

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