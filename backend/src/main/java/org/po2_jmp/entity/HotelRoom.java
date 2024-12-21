package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.RoomGuestCapacity;

@Getter
public class HotelRoom {

    private final int id;
    private final RoomGuestCapacity guestCapacity;
    private final int hotelId;

    public HotelRoom(RoomGuestCapacity guestCapacity, int hotelId) {
        this(0, guestCapacity, hotelId);
    }

    public HotelRoom(int id, RoomGuestCapacity guestCapacity, int hotelId) {
        if (guestCapacity == null) {
            throw new IllegalArgumentException("Guest capacity can not be null" +
                    "but null was passed to HotelRoom constructor");
        }
        this.id = id;
        this.guestCapacity = guestCapacity;
        this.hotelId = hotelId;
    }

}
