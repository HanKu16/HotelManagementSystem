package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserReservationsResponse extends Response {

    private final String userId;
    private final List<ReservationDto> reservations;

    public UserReservationsResponse(ResponseStatus status, String message) {
        this(status, message, null, null);
    }

    public UserReservationsResponse(ResponseStatus status, String message,
            String userId, List<ReservationDto> reservations) {
        super(status, message);
        this.userId = userId;
        this.reservations = reservations;
    }

}
