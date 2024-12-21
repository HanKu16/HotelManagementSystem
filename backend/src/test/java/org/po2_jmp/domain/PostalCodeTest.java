package org.po2_jmp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PostalCodeTest {

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPostalCodeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
           new PostalCode(null);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPostalCodeHasDashInWrongPlace() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PostalCode("123-45");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPostalCodeDoesNotHaveDash() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PostalCode("123456");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPostalCodeHaveNotEnoughCharacters() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PostalCode("1234");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPostalCodeHaveTooManyCharacters() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PostalCode("12-34567");
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenPostalCodeIsValid() {
        PostalCode postalCode = new PostalCode("12-345");
        assertNotNull(postalCode);
    }

}