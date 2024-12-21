package org.po2_jmp.service.contract;

import org.po2_jmp.request.ReservationCreationRequest;
import org.po2_jmp.response.ReservationCreationResponse;

public interface ReservationsCreator {

    ReservationCreationResponse create(ReservationCreationRequest request);

}
