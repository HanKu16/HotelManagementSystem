package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a reservation.
 * <p>
 * This class encapsulates the details of a reservation including the reservation ID,
 * reservation date, creation date and time, hotel name, and room guest capacity.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDto {

    private final int reservationId;
    private final LocalDate reservationDate;
    private final LocalDateTime creationDateTime;
    private final String hotel;
    private final Integer roomGuestCapacity;

}
