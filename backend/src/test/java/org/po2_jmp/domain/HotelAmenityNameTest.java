package org.po2_jmp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HotelAmenityNameTest {

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenHotelAmenityNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelAmenityName(null);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenHotelAmenityNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelAmenityName("");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenHotelAmenityNameIsTooShort() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelAmenityName("a");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenHotelAmenityNameIsTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            String tooLongHotelAmenityName = createTooLongHotelAmenityName();
            new HotelAmenityName(tooLongHotelAmenityName);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenHotelAmenityNameIsValid() {
        HotelAmenityName amenityName = new HotelAmenityName("Wi-Fi");
        assertNotNull(amenityName);
    }

    private String createTooLongHotelAmenityName() {
        return "c".repeat(55);
    }

}


