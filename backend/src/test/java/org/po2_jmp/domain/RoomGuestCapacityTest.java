package org.po2_jmp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoomGuestCapacityTest {

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenCapacityIsTooSmall() {
        assertThrows(IllegalArgumentException.class, () -> {
           new RoomGuestCapacity(0);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenCapacityIsTooBig() {
        assertThrows(IllegalArgumentException.class, () -> {
            new RoomGuestCapacity(7);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenCapacityIsValid() {
        RoomGuestCapacity capacity = new RoomGuestCapacity(4);
        assertNotNull(capacity);
    }

}