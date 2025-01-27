package org.po2_jmp.service.contract;

import org.po2_jmp.request.ReservationCreationRequest;
import org.po2_jmp.response.ReservationCreationResponse;

/**
 * The {@code ReservationsCreator} interface defines the contract for creating a new reservation.
 * Implementations of this interface are responsible for processing the creation of a reservation
 * based on the provided reservation creation request.
 */
public interface ReservationsCreator {

    /**
     * Creates a new reservation based on the provided request data.
     *
     * @param request The {@link ReservationCreationRequest} containing the
     *                reservation details to be created.
     * @return A {@link ReservationCreationResponse} containing the result of
     *                the reservation creation operation.
     */
    ReservationCreationResponse create(ReservationCreationRequest request);

}
