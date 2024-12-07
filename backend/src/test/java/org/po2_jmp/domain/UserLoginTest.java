package org.po2_jmp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginTest {

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenLoginIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
           new UserLogin(null);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenLoginIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserLogin("");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenLoginIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserLogin("  ");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenLoginIsTooShort() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserLogin("John");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenLoginIsTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserLogin("WolfFromTheGreatForest");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenLoginContainsUnderScore() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserLogin("John_London");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenLoginContainsAsterisk() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserLogin("JohnLondon*");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenLoginContainsDollarSign() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserLogin("$JohnLondon");
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenLoginIsValid() {
        UserLogin login = new UserLogin("John12London");
        assertNotNull(login);
    }

}