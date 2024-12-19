package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationCreationResponse extends Response {

    private final String hotelName;
    private final LocalDate reservationDate;

    public ReservationCreationResponse(int statusCode, String message) {
        super(statusCode, message);
        this.hotelName = null;
        this.reservationDate = null;
    }

    public ReservationCreationResponse(int statusCode, String message,
            String hotelName, LocalDate reservationDate) {
        super(statusCode, message);
        this.hotelName = hotelName;
        this.reservationDate = reservationDate;
    }

}
