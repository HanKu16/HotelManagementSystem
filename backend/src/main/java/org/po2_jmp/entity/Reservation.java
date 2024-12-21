package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.UserId;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Reservation {

    private final int id;
    private final LocalDate reservationDate;
    private final LocalDateTime creationDateTime;
    private final UserId userId;
    private final int roomId;

    public Reservation(LocalDate reservationDate,
                       LocalDateTime creationDateTime,
                       UserId userId, int roomId) {
        this(0, reservationDate, creationDateTime, userId, roomId);
    }

    public Reservation(int id, LocalDate reservationDate,
                       LocalDateTime creationDateTime, UserId userId, int roomId) {
        if (areAnyNullParams(reservationDate, creationDateTime, userId)) {
            throw new IllegalArgumentException("Reservation date and " +
                    "creation date time can not be nulls, but null was " +
                    "passed to Reservation constructor");
        }
        this.id = id;
        this.reservationDate = reservationDate;
        this.creationDateTime = creationDateTime;
        this.userId = userId;
        this.roomId = roomId;
    }

    public boolean areAnyNullParams(LocalDate reservationDate,
            LocalDateTime creationDateTime, UserId userId) {
        return (reservationDate == null) || (creationDateTime == null) ||
                (userId == null);
    }

}
