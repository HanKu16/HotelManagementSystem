package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserReservationsResponse extends Response {

    private final String userId;
    private final List<ReservationDto> reservations;
    @JsonCreator
    public UserReservationsResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("userId") String userId,
            @JsonProperty("reservations") List<ReservationDto> reservations) {
        super(status, message);
        this.userId = userId;
        this.reservations = reservations;
    }
    public UserReservationsResponse(ResponseStatus status, String message) {
        this(status, message, null, null);
    }
}
