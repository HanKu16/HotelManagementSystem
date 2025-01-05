package org.po2_jmp.service.contract;

import org.po2_jmp.request.UserReservationsRequest;
import org.po2_jmp.response.UserReservationsResponse;

public interface ReservationsProvider {

    UserReservationsResponse getUserReservations(UserReservationsRequest request);

}
