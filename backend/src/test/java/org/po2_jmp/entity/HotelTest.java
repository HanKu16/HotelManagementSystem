package org.po2_jmp.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.po2_jmp.domain.*;

class HotelTest {

    private final HotelName validName = new HotelName("Babilon");
    private final HotelDescription validDescription = new HotelDescription(
            "This is valid hotel description");
    private final Address validAddress = createValidAddress();

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenHotelNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hotel(null, validDescription, validAddress);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPostalCodeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hotel(validName, null, validAddress);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenBuildingNumberIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hotel(validName, validDescription, null);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenParamsAreValid() {
        Hotel hotel = new Hotel(validName,
                validDescription, validAddress);
        assertNotNull(hotel);
    }

    private Address createValidAddress() {
        return new Address(new CityName("Warszawa"),
                new PostalCode("30-001"), new BuildingNumber("14"));
    }

}
