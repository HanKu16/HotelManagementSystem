package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.HotelAmenityName;

/**
 * Represents an amenity entity associated with a hotel.
 * <p>
 * The {@link HotelAmenity} class encapsulates the details of an amenity,
 * including its name, the associated hotel id, and its unique identifier.
 * It ensures that the hotel amenity name is not null when creating an
 * instance of the amenity.
 * </p>
 */
@Getter
public class HotelAmenity {

    private final int id;
    private final HotelAmenityName name;
    private final int hotelId;

    /**
     * Constructs a {@link HotelAmenity} with the specified name and hotel id.
     * <p>
     * This constructor assigns a default id of 0 to the hotel amenity and
     * throws an {@link IllegalArgumentException} if the provided amenity name is null.
     * </p>
     *
     * @param name the name of the hotel amenity
     * @param hotelId the identifier of the associated hotel
     *
     * @throws IllegalArgumentException if the amenity name is null
     */
    public HotelAmenity(HotelAmenityName name, int hotelId) {
        this(0, name, hotelId);
    }

    /**
     * Constructs a {@link HotelAmenity} with the specified id, name, and hotel id.
     * <p>
     * This constructor allows for the creation of a hotel amenity with a
     * specific id and throws an {@link IllegalArgumentException}
     * if the provided amenity name is null.
     * </p>
     *
     * @param id the unique identifier for the hotel amenity
     * @param amenityName the name of the hotel amenity
     * @param hotelId the identifier of the associated hotel
     *
     * @throws IllegalArgumentException if the amenity name is null
     */
    public HotelAmenity(int id, HotelAmenityName amenityName, int hotelId) {
        if (amenityName == null) {
            throw new IllegalArgumentException("Hotel amenity name can not be null" +
                    "but null was passed to HotelAmenity constructor");
        }
        this.id = id;
        this.name = amenityName;
        this.hotelId = hotelId;
    }

}