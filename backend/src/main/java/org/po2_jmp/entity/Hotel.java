package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.HotelDescription;
import org.po2_jmp.domain.HotelName;

/**
 * Represents a hotel entity.
 * <p>
 * The {@link Hotel} class encapsulates the details of a hotel, including its name,
 * description and address. The constructor ensures that none of these details
 * are null when creating an instance of the hotel.
 */
@Getter
public class Hotel {

    private final int id;
    private final HotelName name;
    private final HotelDescription description;
    private final Address address;

    /**
     * Constructs a {@link Hotel} with the specified name, description, and address.
     * <p>
     * This constructor assigns a default id of 0 to the hotel and throws an
     * {@link IllegalArgumentException} if any of the provided parameters are null.
     *
     * @param name the name of the hotel
     * @param description the description of the hotel
     * @param address the address of the hotel
     *
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public Hotel(HotelName name, HotelDescription description, Address address) {
        this(0, name, description, address);
    }

    /**
     * Constructs a {@link Hotel} with the specified id, name, description, and address.
     * <p>
     * This constructor allows for the creation of a hotel with a specific id,
     * and throws an {@link IllegalArgumentException} if any of the provided
     * parameters are null.
     *
     * @param id the unique identifier for the hotel
     * @param name the name of the hotel
     * @param description the description of the hotel
     * @param address the address of the hotel
     *
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public Hotel(int id, HotelName name, HotelDescription description,
                 Address address) {
        if (areAnyNullParams(name, description, address)) {
            throw new IllegalArgumentException("Hotel name, description, " +
                    "building number can not be passed as null to Hotel " +
                    "constructor");
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
    }

    private boolean areAnyNullParams(HotelName name,
            HotelDescription description, Address address) {
        return (name == null) || (description == null) ||
                (address == null);
    }

}
