package org.po2_jmp.service.contract;

import org.po2_jmp.request.UserReservationsRequest;
import org.po2_jmp.response.UserReservationsResponse;

/**
 * The {@code ReservationsProvider} interface defines the contract for retrieving a
 * user's reservations. Implementations of this interface are responsible for processing
 * the request to fetch a user's reservations based on the provided request data.
 */
public interface ReservationsProvider {

    /**
     * Retrieves the reservations for a user based on the provided request data.
     *
     * @param request The {@link UserReservationsRequest} containing the user's
     *        information and the request details.
     * @return A {@link UserReservationsResponse} containing the list of
     *        reservations for the user.
     */
    UserReservationsResponse getUserReservations(UserReservationsRequest request);

}
