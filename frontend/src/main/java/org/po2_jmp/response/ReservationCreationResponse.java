package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationCreationResponse extends Response {

    private final String hotelName;
    private final LocalDate reservationDate;

    @JsonCreator
    public ReservationCreationResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("hotelName") String hotelName,
            @JsonProperty("reservationDate") LocalDate reservationDate) {
        super(status, message);
        this.hotelName = hotelName;
        this.reservationDate = reservationDate;
    }
}
