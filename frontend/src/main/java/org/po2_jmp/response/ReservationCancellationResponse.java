package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationCancellationResponse extends Response {

    private final Integer reservationId;

    public ReservationCancellationResponse() {
        super(null, null);
        this.reservationId = null;
    }

    @JsonCreator
    public ReservationCancellationResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("reservationId") Integer reservationId) {
        super(status, message);
        this.reservationId = reservationId;
    }
}
