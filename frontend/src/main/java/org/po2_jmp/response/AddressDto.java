package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class AddressDto {

    private final String city;
    private final String street;
    private final String postalCode;
    private final String buildingNumber;

    @JsonCreator
    public AddressDto(
            @JsonProperty("city") String city,
            @JsonProperty("street") String street,
            @JsonProperty("postalCode") String postalCode,
            @JsonProperty("buildingNumber") String buildingNumber) {
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.buildingNumber = buildingNumber;
    }
}
