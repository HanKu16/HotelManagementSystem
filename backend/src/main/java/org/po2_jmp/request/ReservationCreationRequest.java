package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class ReservationCreationRequest extends Request {

    private final String userId;
    private final LocalDate reservationDate;
    private final int hotelId;
    private final int guestCapacity;

    @JsonCreator
    public ReservationCreationRequest(
            @JsonProperty("command") String command,
            @JsonProperty("userId") String userId,
            @JsonProperty("reservationDate") LocalDate reservationDate,
            @JsonProperty("hotelId") int hotelId,
            @JsonProperty("guestCapacity") int guestCapacity) {
        super(command);
        this.userId = userId;
        this.reservationDate = reservationDate;
        this.hotelId = hotelId;
        this.guestCapacity = guestCapacity;
    }

}
