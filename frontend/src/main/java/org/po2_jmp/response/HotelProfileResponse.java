package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelProfileResponse extends Response {

    private final Integer hotelId;
    private final String name;
    private final String description;
    private final List<String> amenities;
    private final AddressDto address;
    private final List<Integer> guestCapacities;

    @JsonCreator
    public HotelProfileResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("hotelId") Integer hotelId,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("amenities") List<String> amenities,
            @JsonProperty("address") AddressDto address,
            @JsonProperty("guestCapacities") List<Integer> guestCapacities) {
        super(status, message);
        this.hotelId = hotelId;
        this.name = name;
        this.description = description;
        this.amenities = amenities;
        this.address = address;
        this.guestCapacities = guestCapacities;
    }
}
