package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDto {

    private final int reservationId;
    private final LocalDate reservationDate;
    private final LocalDateTime creationDateTime;
    private final String hotel;
    private final Integer roomGuestCapacity;

    @JsonCreator
    public ReservationDto(
            @JsonProperty("reservationId") int reservationId,
            @JsonProperty("reservationDate") LocalDate reservationDate,
            @JsonProperty("creationDateTime") LocalDateTime creationDateTime,
            @JsonProperty("hotel") String hotel,
            @JsonProperty("roomGuestCapacity") Integer roomGuestCapacity
    ) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.creationDateTime = creationDateTime;
        this.hotel = hotel;
        this.roomGuestCapacity = roomGuestCapacity;
    }
}
