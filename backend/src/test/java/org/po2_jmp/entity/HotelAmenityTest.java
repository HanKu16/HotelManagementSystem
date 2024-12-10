package org.po2_jmp.entity;

import org.junit.jupiter.api.Test;
import org.po2_jmp.domain.HotelAmenityName;
import static org.junit.jupiter.api.Assertions.*;

class HotelAmenityTest {

    private final HotelAmenityName validHotelAmenityName = new HotelAmenityName("Basen");

    @Test
    void ConstructorWithoutId_ShouldThrowIllegalArgumentException_WhenHotelAmenityNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelAmenity(null, 5);
        });
    }

    @Test
    void ConstructorWithoutId_ShouldCreateObject_WhenParamsAreValid() {
        HotelAmenity amenity = new HotelAmenity(validHotelAmenityName, 10);
        assertNotNull(amenity);
    }

    @Test
    void ConstructorWitId_ShouldThrowIllegalArgumentException_WhenHotelAmenityNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelAmenity(1, null, 5);
        });
    }

    @Test
    void ConstructorWithId_ShouldCreateObject_WhenParamsAreValid() {
        HotelAmenity amenity = new HotelAmenity(3, validHotelAmenityName, 10);
        assertNotNull(amenity);
    }

}

