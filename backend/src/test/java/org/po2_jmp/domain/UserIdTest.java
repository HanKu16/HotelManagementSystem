package org.po2_jmp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserIdTest {

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
           new UserId(null);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenIdIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserId("");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenIdIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserId("        ");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenIdIsTooShort() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserId("John");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenIdIsTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserId("WolfFromTheGreatForest");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenIdContainsUnderScore() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserId("John_London");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenIdContainsAsterisk() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserId("JohnLondon*");
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenIdContainsDollarSign() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserId("$JohnLondon");
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenIdIsValid() {
        UserId userId = new UserId("John12London");
        assertNotNull(userId);
    }

}