package org.po2_jmp.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.po2_jmp.domain.BuildingNumber;
import org.po2_jmp.domain.CityName;
import org.po2_jmp.domain.PostalCode;
import org.po2_jmp.domain.StreetName;

class AddressTest {

    private final CityName validCityName = new CityName("Warszawa");
    private final StreetName validStreetName = new StreetName("Długa");
    private final PostalCode validPostalCode = new PostalCode("30-001");
    private final BuildingNumber validBuildingNumber = new BuildingNumber("14");

    @Test
    void ConstructorWithoutId_ShouldThrowIllegalArgumentException_WhenCityIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Address(null, validStreetName, validPostalCode, validBuildingNumber);
        });
    }

    @Test
    void ConstructorWithoutId_ShouldThrowIllegalArgumentException_WhenStreetIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Address(validCityName, null, validPostalCode, validBuildingNumber);
        });
    }

    @Test
    void ConstructorWithoutId_ShouldThrowIllegalArgumentException_WhenPostalCodeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Address(validCityName, validStreetName, null, validBuildingNumber);
        });
    }

    @Test
    void ConstructorWithoutId_ShouldThrowIllegalArgumentException_WhenBuildingNumberIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Address(validCityName, validStreetName, validPostalCode, null);
        });
    }

    @Test
    void ConstructorWithoutId_ShouldCreateObject_WhenParamsAreValid() {
        Address address = new Address(validCityName, validStreetName,
                validPostalCode, validBuildingNumber);
        assertNotNull(address);
    }

    @Test
    void ConstructorWithId_ShouldThrowIllegalArgumentException_WhenCityIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Address(8, null, validStreetName,
                    validPostalCode, validBuildingNumber);
        });
    }

    @Test
    void ConstructorWithId_ShouldThrowIllegalArgumentException_WhenStreetIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Address(8, validCityName, null,
                    validPostalCode, validBuildingNumber);
        });
    }

    @Test
    void ConstructorWithId_ShouldThrowIllegalArgumentException_WhenPostalCodeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Address(100, validCityName, validStreetName,
                    null, validBuildingNumber);
        });
    }

    @Test
    void ConstructorWithId_ShouldThrowIllegalArgumentException_WhenBuildingNumberIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Address(17, validCityName, validStreetName,
                    validPostalCode, null);
        });
    }

    @Test
    void ConstructorWithId_ShouldCreateObject_WhenParamsAreValid() {
        Address address = new Address(55, validCityName, validStreetName,
                validPostalCode, validBuildingNumber);
        assertNotNull(address);
    }

}

