package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.BuildingNumber;
import org.po2_jmp.domain.CityName;
import org.po2_jmp.domain.PostalCode;
import org.po2_jmp.domain.StreetName;

@Getter
public class Address {

    private final int id;
    private final CityName city;
    private final StreetName street;
    private final PostalCode postalCode;
    private final BuildingNumber buildingNumber;

    public Address(CityName cityName, StreetName streetName, PostalCode postalCode,
            BuildingNumber buildingNumber) {
        this(0, cityName, streetName, postalCode, buildingNumber);
    }

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
