package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class ReservationCancellationRequest extends Request {

    private final Integer reservationId;

    @JsonCreator
    public ReservationCancellationRequest(
            @JsonProperty("command") String command,
            @JsonProperty("reservationId") int reservationId) {
        super(command);
        this.reservationId = reservationId;
    }

}
