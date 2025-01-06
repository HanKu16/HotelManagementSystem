package org.po2_jmp.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class AddressDto {

    private final String city;
    private final String street;
    private final String postalCode;
    private final String buildingNumber;

}
