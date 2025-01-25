package org.po2_jmp.service.implementation;

import java.time.LocalDate;
import java.util.Optional;
import org.po2_jmp.entity.Reservation;
import org.po2_jmp.repository.contract.ReservationsRepository;
import org.po2_jmp.request.ReservationCancellationRequest;
import org.po2_jmp.response.ReservationCancellationResponse;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.service.contract.ReservationsCanceler;

public class ReservationsCancelerImpl implements ReservationsCanceler {

    private final ReservationsRepository reservationsRepository;

    public ReservationsCancelerImpl(ReservationsRepository reservationsRepository) {
        if (reservationsRepository == null) {
            throw new IllegalArgumentException("ReservationsRepository can " +
                    "not be null, but null was passed to ReservationsCancelerImpl");
        }
        this.reservationsRepository = reservationsRepository;
    }

    @Override
    public ReservationCancellationResponse cancel(
            ReservationCancellationRequest request) {
        Integer reservationId = request.getReservationId();
        if (reservationId == null) {
            return new ReservationCancellationResponse(ResponseStatus.BAD_REQUEST,
                    "Can't delete reservation of id null");
        }
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
