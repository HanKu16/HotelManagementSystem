package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.BuildingNumber;
import org.po2_jmp.domain.CityName;
import org.po2_jmp.domain.PostalCode;
import org.po2_jmp.domain.StreetName;

/**
 * Represents an address entity.
 * <p>
 * The {@link Address} class stores the address information, which includes
 * a city, street name, postal code, and building number. It provides validation
 * to ensure that these values are not null when creating an instance.
 */
@Getter
public class Address {

    private final int id;
    private final CityName city;
    private final StreetName street;
    private final PostalCode postalCode;
    private final BuildingNumber buildingNumber;

    /**
     * Constructs an {@link Address} with the specified city name, street name,
     * postal code, and building number.
     * <p>
     * The address is created with a default id of 0, and it will throw an
     * {@link IllegalArgumentException} if any of the provided parameters are null.
     *
     * @param cityName the name of the city
     * @param streetName the name of the street
     * @param postalCode the postal code for the address
     * @param buildingNumber the building number for the address
     *
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public Address(CityName cityName, StreetName streetName, PostalCode postalCode,
            BuildingNumber buildingNumber) {
        this(0, cityName, streetName, postalCode, buildingNumber);
    }

    /**
     * Constructs an {@link Address} with the specified id, city name,
     * street name, postal code, and building number.
     * <p>
     * This constructor allows for the creation of an address with
     * a specified id, throwing an {@link IllegalArgumentException}
     * if any of the parameters are null.
     *
     * @param id the unique identifier for the address
     * @param cityName the name of the city
     * @param streetName the name of the street
     * @param postalCode the postal code for the address
     * @param buildingNumber the building number for the address
     *
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public Address(int id, CityName cityName, StreetName streetName,
                   PostalCode postalCode, BuildingNumber buildingNumber) {
        if (areAnyNullParams(cityName, streetName, postalCode, buildingNumber)) {
            throw new IllegalArgumentException("City name, street name, postal code, " +
                    "building number can not be passed as null to Address " +
                    "constructor");
        }
        this.id = id;
        this.city = cityName;
        this.street = streetName;
        this.postalCode = postalCode;
        this.buildingNumber = buildingNumber;
    }

    private boolean areAnyNullParams(CityName cityName, StreetName streetName,
            PostalCode postalCode, BuildingNumber buildingNumber) {
        return (cityName == null) || (postalCode == null) ||
            (streetName == null) || (buildingNumber == null);
    }

}
