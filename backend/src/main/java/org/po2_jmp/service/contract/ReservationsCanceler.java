package org.po2_jmp.service.contract;

import org.po2_jmp.request.ReservationCancellationRequest;
import org.po2_jmp.response.ReservationCancellationResponse;

public interface ReservationsCanceler {

    ReservationCancellationResponse cancel(ReservationCancellationRequest request);

}
