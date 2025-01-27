package org.po2_jmp.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents an address data transfer object (DTO) containing details about a specific address.
 * <p>
 * This class includes fields for the city, street, postal code, and building number.
 * </p>
 */
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
