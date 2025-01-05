package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

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

    public HotelProfileResponse(ResponseStatus status, String message) {
        super(status, message);
        this.hotelId = null;
        this.name = null;
        this.description = null;
        this.amenities = null;
        this.address = null;
    }

    public HotelProfileResponse(ResponseStatus status, String message,
            int hotelId, String hotelName, String description,
            List<String> amenities, AddressDto address) {
        super(status, message);
        this.hotelId = hotelId;
        this.name = hotelName;
        this.description = description;
        this.amenities = amenities;
        this.address = address;
    }

}
