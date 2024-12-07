package org.po2_jmp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HotelNameTest {

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenHotelNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelName(null);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenHotelNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelName("");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenHotelIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelName("    ");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenHotelNameIsTooShort() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelName("a");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenHotelNameIsTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelName("ThisNameIsTooLongToBeValidHotelNameSoTestShouldFail");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenHotelContainsUnderScore() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelName("great_hotel");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenHotelNameContainsDollarSign() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelName("Great Hotel$");
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenHotelNameIsValid() {
        HotelName hotelName = new HotelName("Great Hotel of Poland");
        assertNotNull(hotelName);
    }

}