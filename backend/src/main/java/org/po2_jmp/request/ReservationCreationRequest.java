package org.po2_jmp.request;

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

    public ReservationCreationRequest(String type, String endpoint, String userId,
            LocalDate reservationDate, int hotelId, int guestCapacity) {
        super(type, endpoint);
        this.userId = userId;
        this.reservationDate = reservationDate;
        this.hotelId = hotelId;
        this.guestCapacity = guestCapacity;
    }

}
