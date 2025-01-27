package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

/**
 * Represents a data transfer object (DTO) for providing an overview of a hotel.
 * <p>
 * This class contains information about a hotel, including its ID, name, amenities, and address.
 * </p>
 */
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
