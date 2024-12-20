package org.po2_jmp.service.helper;

import org.po2_jmp.repository.contract.HotelsRepository;
import org.po2_jmp.repository.contract.UsersRepository;
import org.po2_jmp.request.ReservationCreationRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationCreationRequestValidator {

    private final UsersRepository usersRepository;
    private final HotelsRepository hotelsRepository;

    public ReservationCreationRequestValidator(
            UsersRepository usersRepository,
            HotelsRepository hotelsRepository) {
        if (usersRepository == null) {
            throw new IllegalArgumentException("UsersRepository can not be null" +
                    " but null was passed to ReservationCreationRequestValidator");
        }
        if (hotelsRepository == null) {
            throw new IllegalArgumentException("HotelsRepository can not be null" +
                    " but null was passed to ReservationCreationRequestValidator");
        }
        this.usersRepository = usersRepository;
        this.hotelsRepository = hotelsRepository;
    }

    public List<String> validate(ReservationCreationRequest request) {
        List<String> errorMessages = new ArrayList<>();
        if (!isReservationDateValid(request.getReservationDate())) {
            errorMessages.add("reservation date is invalid");
        }
        if (!doesUserExist(request.getUserId())) {
            errorMessages.add("user does not exist");
        }
        if (!doesHotelExist(request.getHotelId())) {
            errorMessages.add("hotel does not exist");
        }
        return errorMessages;
    }

    private boolean isReservationDateValid(LocalDate reservationDate) {
        LocalDate today = LocalDate.now();
        return (reservationDate != null) && (reservationDate.isEqual(today) ||
                reservationDate.isAfter(today));
    }

    private boolean doesUserExist(String userId) {
        return (userId != null) && usersRepository.findById(userId).isPresent();
    }

    private boolean doesHotelExist(int hotelId) {
        return hotelsRepository.findById(hotelId).isPresent();
    }

}
