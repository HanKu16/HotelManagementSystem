package org.po2_jmp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CityNameTest {

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenCityNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CityName(null);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenCityNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CityName("");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenCityNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CityName("        ");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenCityNameIsTooShort() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CityName("A");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenCityNameIsTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            String tooLongCityName = createTooLongCityName();
            new CityName(tooLongCityName);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenCityNameContainsUnderScore() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CityName("War_szawa");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenCityNameContainsAsterisk() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CityName("Kielce*");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenCityNameContainsDollarSign() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CityName("$Szczecin");
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenCityNameIsWarszawa() {
        CityName cityName = new CityName("Warszawa");
        assertNotNull(cityName);
    }

    @Test
    void Constructor_ShouldCreateObject_WhenCityNameIsKraków() {
        CityName cityName = new CityName("Kraków");
        assertNotNull(cityName);
    }

    @Test
    void Constructor_ShouldCreateObject_WhenCityNameIsPiotrkówTrybunalski() {
        CityName cityName = new CityName("Piotrków Trybunalski");
        assertNotNull(cityName);
    }

    private String createTooLongCityName() {
        return "b".repeat(70);
    }

}