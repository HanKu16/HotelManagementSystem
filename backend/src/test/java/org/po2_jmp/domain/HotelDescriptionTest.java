package org.po2_jmp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HotelDescriptionTest {

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenDescriptionIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HotelDescription(null);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenDescriptionIsTooLong() {
        assertThrows(IllegalArgumentException.class, () -> {
            String tooLongDescription = createTooLongDescription();
            new HotelDescription(tooLongDescription);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenDescriptionIsValid() {
        HotelDescription description = new HotelDescription(
                "This is description of our 5 Star Hotel!");
        assertNotNull(description);
    }

    private String createTooLongDescription() {
        return "a".repeat(1002);
    }

}