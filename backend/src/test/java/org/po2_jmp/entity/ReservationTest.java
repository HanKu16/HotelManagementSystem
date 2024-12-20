package org.po2_jmp.entity;

import org.junit.jupiter.api.Test;
import org.po2_jmp.domain.UserId;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private final int validReservationId = 15;
    private final LocalDate validReservationDate = LocalDate.of(2024, 12, 31);
    private final LocalDateTime validCreationDateTime = LocalDateTime.of(
            2024, 12, 27, 14, 30, 0);
    private final UserId validUserId = new UserId("szpaku");
    private final int validRoomId = 3;


    @Test
    void ConstructorWithoutId_ThrowsIllegalArgumentException_WhenReservationDateIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation(null, validCreationDateTime,
                    validUserId, validRoomId);
        });
    }

    @Test
    void ConstructorWithoutId_ThrowsIllegalArgumentException_WhenCreationDateTmeTIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation(validReservationDate, null, validUserId, validRoomId);
        });
    }

    @Test
    void ConstructorWithoutId_ThrowsIllegalArgumentException_WhenUserIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation(validReservationDate, validCreationDateTime,
                    null, validRoomId);
        });
    }

    @Test
    void ConstructorWithId_ThrowsIllegalArgumentException_WhenReservationDateIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation(validReservationId, null, validCreationDateTime,
                    validUserId, validRoomId);
        });
    }

    @Test
    void ConstructorWithId_ThrowsIllegalArgumentException_WhenCreationDateTmeTIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation(validReservationId, validReservationDate,
                    null, validUserId, validRoomId);
        });
    }

    @Test
    void ConstructorWithId_ThrowsIllegalArgumentException_WhenUserIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation(validReservationId, validReservationDate,
                    validCreationDateTime, null, validRoomId);
        });
    }

}
