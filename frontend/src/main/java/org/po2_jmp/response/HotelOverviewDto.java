package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelOverviewDto {

    private final Integer hotelId;
    private final String name;
    private final List<String> amenities;
    private final AddressDto address;

}
