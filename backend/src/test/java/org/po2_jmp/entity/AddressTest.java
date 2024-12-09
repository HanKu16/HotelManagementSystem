package org.po2_jmp.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.po2_jmp.domain.BuildingNumber;
import org.po2_jmp.domain.CityName;
import org.po2_jmp.domain.PostalCode;

class AddressTest {

    private final CityName validCityName = new CityName("Warszawa");
    private final PostalCode validPostalCode = new PostalCode("30-001");
    private final BuildingNumber validBuildingNumber = new BuildingNumber("14");

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenCityIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Address(null, validPostalCode, validBuildingNumber);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPostalCodeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Address(validCityName, null, validBuildingNumber);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenBuildingNumberIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Address(validCityName, validPostalCode, null);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenParamsAreValid() {
        Address address = new Address(validCityName,
                validPostalCode, validBuildingNumber);
        assertNotNull(address);
    }

}

