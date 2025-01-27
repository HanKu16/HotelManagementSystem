package org.po2_jmp.service.implementation;

import java.time.LocalDate;
import java.util.Optional;
import org.po2_jmp.entity.Reservation;
import org.po2_jmp.repository.contract.ReservationsRepository;
import org.po2_jmp.request.ReservationCancellationRequest;
import org.po2_jmp.response.ReservationCancellationResponse;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.service.contract.ReservationsCanceler;

/**
 * The {@code ReservationsCancelerImpl} class implements the {@link ReservationsCanceler}
 * interface. It is responsible for handling reservation cancellations. The class
 * interacts with the {@link ReservationsRepository} to check for the existence of
 * a reservation, ensure it is cancelable.
 */
public class ReservationsCancelerImpl implements ReservationsCanceler {

    private final ReservationsRepository reservationsRepository;

    /**
     * Constructs a new {@code ReservationsCancelerImpl} instance with the
     * specified {@link ReservationsRepository}.
     *
     * @param reservationsRepository The repository used to fetch and delete reservations.
     * @throws IllegalArgumentException if the {@code reservationsRepository} is {@code null}.
     */
    public ReservationsCancelerImpl(ReservationsRepository reservationsRepository) {
        if (reservationsRepository == null) {
            throw new IllegalArgumentException("ReservationsRepository can " +
                    "not be null, but null was passed to ReservationsCancelerImpl");
        }
        this.reservationsRepository = reservationsRepository;
    }

    /**
     * Cancels a reservation based on the provided {@link ReservationCancellationRequest}.
     * It checks if the reservation exists, ensures the reservation date is in the future,
     * and if so, deletes the reservation. Otherwise, it returns an appropriate response.
     *
     * @param request The {@link ReservationCancellationRequest} containing the
     *        reservation ID to be canceled.
     * @return A {@link ReservationCancellationResponse} indicating the result
     *        of the cancellation attempt.
     */
    @Override
    public ReservationCancellationResponse cancel(
            ReservationCancellationRequest request) {
        int reservationId = request.getReservationId();
        Optional<Reservation> optionalReservation = reservationsRepository
                .findById(request.getReservationId());
        if (optionalReservation.isEmpty()) {
            return new ReservationCancellationResponse(ResponseStatus.NOT_FOUND,
                    "Reservation of id " + reservationId + " does not exist");
        }
        LocalDate reservationDate = optionalReservation.get().getReservationDate();
        if (!isDateFromTheFuture(reservationDate)) {
            return new ReservationCancellationResponse(ResponseStatus.BAD_REQUEST,
                    "Can cancel only reservations from the future date");
        }
        return deleteReservation(reservationId);
    }

    private boolean isDateFromTheFuture(LocalDate date) {
        LocalDate today = LocalDate.now();
        return date.isAfter(today);
    }

    private ReservationCancellationResponse deleteReservation(int reservationId) {
        return (reservationsRepository.deleteById(reservationId) == 1) ?
                new ReservationCancellationResponse(ResponseStatus.OK,
                        "Reservation deleted", reservationId) :
                new ReservationCancellationResponse(ResponseStatus.INTERNAL_SERVER_ERROR,
                        "Something goes wrong on the server");
    }

}
