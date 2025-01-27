package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

/**
 * Represents the response containing detailed information about a hotel profile.
 * <p>
 * This class extends {@link Response} and includes various fields to describe the hotel,
 * such as its ID, name, description, amenities, address, and available guest capacities.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelProfileResponse extends Response {

    private final Integer hotelId;
    private final String name;
    private final String description;
    private final List<String> amenities;
    private final AddressDto address;
    private final List<Integer> guestCapacities;

    /**
     * Constructs a {@code HotelProfileResponse} with the specified status and message,
     * but without hotel details. Should be used for error responses.
     *
     * @param status the status of the response.
     * @param message the message associated with the response.
     */
    public HotelProfileResponse(ResponseStatus status, String message) {
        super(status, message);
        this.hotelId = null;
        this.name = null;
        this.description = null;
        this.amenities = null;
        this.address = null;
        this.guestCapacities = null;
    }

    /**
     * Constructs a {@code HotelProfileResponse} with detailed hotel information.
     *
     * @param status the status of the response.
     * @param message the message associated with the response.
     * @param hotelId the unique identifier for the hotel.
     * @param hotelName the name of the hotel.
     * @param description a brief description of the hotel.
     * @param amenities a list of amenities available at the hotel.
     * @param address the address of the hotel, represented as an {@link AddressDto}.
     * @param guestCapacities a list of guest capacities available for the hotel.
     */
    public HotelProfileResponse(ResponseStatus status, String message,
            int hotelId, String hotelName, String description,
            List<String> amenities, AddressDto address,
            List<Integer> guestCapacities) {
        super(status, message);
        this.hotelId = hotelId;
        this.name = hotelName;
        this.description = description;
        this.amenities = amenities;
        this.address = address;
        this.guestCapacities = guestCapacities;
    }

}
