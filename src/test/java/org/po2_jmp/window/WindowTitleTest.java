package org.po2_jmp.window;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WindowTitleTest {

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenTitleIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowTitle(null);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenTitleIsLongerThanAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
           new WindowTitle(createTooLongWindowTileName());
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenTitleIsValid() {
        WindowTitle title = new WindowTitle("HotelManagementSystem");
        assertNotNull(title);
    }

    @Test
    void GetValue_ShouldReturnValue() {
        WindowTitle title = new WindowTitle("HotelManagementSystem");
        assertEquals("HotelManagementSystem", title.getValue());
    }

    private String createTooLongWindowTileName() {
        return "a".repeat(100);
    }

}
