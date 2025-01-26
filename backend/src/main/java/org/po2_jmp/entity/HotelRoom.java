package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.RoomGuestCapacity;

/**
 * Represents a hotel room entity.
 * <p>
 * The {@link HotelRoom} class encapsulates the details of a hotel room,
 * including its guest capacity, the unique identifier of the room, and the
 * identifier of the associated hotel. It ensures that the
 * guest capacity is not null when creating an instance of the room.
 * </p>
 */
@Getter
public class HotelRoom {

    private final int id;
    private final RoomGuestCapacity guestCapacity;
    private final int hotelId;

    /**
     * Constructs a {@link HotelRoom} with the specified guest capacity and hotel id.
     * <p>
     * This constructor assigns a default id of 0 to the hotel room and throws an
     * {@link IllegalArgumentException} if the provided guest capacity is null.
     * </p>
     *
     * @param guestCapacity the guest capacity of the hotel room
     * @param hotelId the identifier of the associated hotel
     *
     * @throws IllegalArgumentException if the guest capacity is null
     */
    public HotelRoom(RoomGuestCapacity guestCapacity, int hotelId) {
        this(0, guestCapacity, hotelId);
    }

    /**
     * Constructs a {@link HotelRoom} with the specified id, guest capacity, and hotel id.
     * <p>
     * This constructor allows for the creation of a hotel room with a specific id
     * and throws an {@link IllegalArgumentException} if the provided guest
     * capacity is null.
     * </p>
     *
     * @param id the unique identifier for the hotel room
     * @param guestCapacity the guest capacity of the hotel room
     * @param hotelId the identifier of the associated hotel
     *
     * @throws IllegalArgumentException if the guest capacity is null
     */
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
