package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.BuildingNumber;
import org.po2_jmp.domain.CityName;
import org.po2_jmp.domain.PostalCode;

@Getter
public class Address {

    private final int id;
    private final CityName city;
    private final PostalCode postalCode;
    private final BuildingNumber buildingNumber;

    public Address(CityName cityName, PostalCode postalCode,
            BuildingNumber buildingNumber) {
        this(0, cityName, postalCode, buildingNumber);
    }

    public Address(int id, CityName cityName, PostalCode postalCode,
                   BuildingNumber buildingNumber) {
        if (areAnyNullParams(cityName, postalCode, buildingNumber)) {
            throw new IllegalArgumentException("City name, postal code, " +
                    "building number can not be passed as null to Address " +
                    "constructor");
        }
        this.id = id;
        this.city = cityName;
        this.postalCode = postalCode;
        this.buildingNumber = buildingNumber;
    }

    private boolean areAnyNullParams(CityName cityName,
            PostalCode postalCode, BuildingNumber buildingNumber) {
        return (cityName == null) || (postalCode == null) || (buildingNumber == null);
    }

}
