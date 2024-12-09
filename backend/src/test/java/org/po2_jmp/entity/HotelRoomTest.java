package org.po2_jmp.entity;

import org.junit.jupiter.api.Test;
import org.po2_jmp.domain.RoomGuestCapacity;
import static org.junit.jupiter.api.Assertions.*;

class HotelRoomTest {

    @Test
    void ConstructorWithoutId_ShouldThrowIllegalArgumentException_WhenGuestCapacityIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelRoom(null, 5);
        });
    }

    @Test
    void ConstructorWithoutId_ShouldCreateObject_WhenParamsAreValid() {
        HotelRoom hotelRoom = new HotelRoom(new RoomGuestCapacity(3), 2);
        assertNotNull(hotelRoom);
    }

    @Test
    void ConstructorWithId_ShouldThrowIllegalArgumentException_WhenGuestCapacityIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
           new HotelRoom(1, null, 5);
        });
    }

    @Test
    void ConstructorWithId_ShouldCreateObject_WhenParamsAreValid() {
        HotelRoom hotelRoom = new HotelRoom(1, new RoomGuestCapacity(3), 2);
        assertNotNull(hotelRoom);
    }

}
