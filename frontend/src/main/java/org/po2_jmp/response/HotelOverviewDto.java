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
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelOverviewDto {

    private final Integer hotelId;
    private final String name;
    private final List<String> amenities;
    private final AddressDto address;

    @JsonCreator
    public HotelOverviewDto(
            @JsonProperty("hotelId") Integer hotelId,
            @JsonProperty("name") String name,
            @JsonProperty("amenities") List<String> amenities,
            @JsonProperty("address") AddressDto address) {
        this.hotelId = hotelId;
        this.name = name;
        this.amenities = amenities;
        this.address = address;
    }
}
