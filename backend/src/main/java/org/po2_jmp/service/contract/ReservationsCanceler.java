package org.po2_jmp.service.contract;

import org.po2_jmp.request.ReservationCancellationRequest;
import org.po2_jmp.response.ReservationCancellationResponse;

/**
 * The {@code ReservationsCanceler} interface defines the contract for canceling a reservation.
 * Implementations of this interface are responsible for implementing the logic to cancel a reservation
 * based on a cancellation request.
 */
public interface ReservationsCanceler {

    /**
     * Cancels a reservation based on the provided request.
     *
     * @param request The {@link ReservationCancellationRequest} containing the
     *        reservation details to be canceled.
     * @return A {@link ReservationCancellationResponse} indicating the result
     *        of the cancellation attempt.
     */
    ReservationCancellationResponse cancel(ReservationCancellationRequest request);

}
