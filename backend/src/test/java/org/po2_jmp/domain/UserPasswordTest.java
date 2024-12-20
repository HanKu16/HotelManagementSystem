package org.po2_jmp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserPasswordTest {

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPasswordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserPassword(null);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPasswordIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserPassword("");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPasswordIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserPassword("        ");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPasswordIsTooShort() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserPassword("dog");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenLoginIsTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserPassword("ThisPasswordIsTooLongSoTestShouldFail");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPasswordContainsUnderScore() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserPassword("my_horse");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPasswordContainsAsterisk() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserPassword("*goodHorse");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPasswordContainsDollarSign() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserPassword("greatHorse$");
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenLoginIsValid() {
        UserId login = new UserId("MyHorse15");
        assertNotNull(login);
    }

}