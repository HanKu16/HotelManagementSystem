package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a request to retrieve the profile of a specific hotel.
 * <p>
 * This class extends the {@link Request} class and includes additional
 * information about the hotel ID associated with the request.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class HotelProfileRequest extends Request {

    private final int hotelId;

    /**
     * Constructs a {@code HotelProfileRequest} with the specified command and hotel ID.
     *
     * @param command the command associated with the request (inherited from {@link Request}).
     * @param hotelId the unique identifier of the hotel.
     */
    @JsonCreator
    public HotelProfileRequest(
            @JsonProperty("command") String command,
            @JsonProperty("hotelId") int hotelId) {
        super(command);
        this.hotelId = hotelId;
    }

}
