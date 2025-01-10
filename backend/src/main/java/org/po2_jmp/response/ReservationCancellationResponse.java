package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationCancellationResponse extends Response {

    private final Integer reservationId;

    public ReservationCancellationResponse(
            ResponseStatus status, String message) {
        this(status, message, null);
    }

    public ReservationCancellationResponse(ResponseStatus status,
            String message, Integer reservationId) {
        super(status, message);
        this.reservationId = reservationId;
    }

}
