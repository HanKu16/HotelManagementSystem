package org.po2_jmp.service.helper;

import org.po2_jmp.repository.contract.HotelsRepository;
import org.po2_jmp.repository.contract.UsersRepository;
import org.po2_jmp.request.ReservationCreationRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ReservationCreationRequestValidator} class is responsible for validating
 * a {@link ReservationCreationRequest} before it is processed further. It checks whether
 * the reservation date, the user, and the hotel in the request are valid.
 *
 * <p>This class ensures that:</p>
 * <ul>
 *   <li>The reservation date is today or in the future.</li>
 *   <li>The user exists in the system.</li>
 *   <li>The specified hotel exists in the system.</li>
 * </ul>
 */
public class ReservationCreationRequestValidator {

    private final UsersRepository usersRepository;
    private final HotelsRepository hotelsRepository;

    /**
     * Constructs a new {@code ReservationCreationRequestValidator} with the specified
     * {@link UsersRepository} and {@link HotelsRepository}.
     *
     * @param usersRepository The {@link UsersRepository} used to check if the user exists.
     * @param hotelsRepository The {@link HotelsRepository} used to check if the hotel exists.
     * @throws IllegalArgumentException if either {@link UsersRepository} or
     *         {@link HotelsRepository} is {@code null}.
     */
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

    /**
     * Validates a {@link ReservationCreationRequest} by checking the reservation date,
     * the existence of the user, and the existence of the hotel.
     *
     * @param request The {@link ReservationCreationRequest} to be validated.
     * @return A list of error messages. If the request is valid, the list will be empty.
     */
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
