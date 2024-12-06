package org.po2_jmp.window;

import org.junit.jupiter.api.Test;
import org.po2_jmp.panels.PanelId;
import static org.junit.jupiter.api.Assertions.*;

class WindowSettingsTest {

    private final WindowDimensions validDimensions = new WindowDimensions(800, 600);
    private final WindowTitle validTitle = new WindowTitle("HotelManagementSystem");
    private final PanelId validPanelId = new PanelId("LoginPanel");

    @Test
    void Constructor_ShouldThrowIllegalArgumentExceptions_WhenDimensionsIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowSettings(null, validTitle, validPanelId);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentExceptions_WhenTitleIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowSettings(validDimensions, null, validPanelId);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentExceptions_WhenWindowPanelIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowSettings(validDimensions, validTitle, null);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenArgumentsAreValid() {
        WindowSettings settings = new WindowSettings(
                validDimensions, validTitle, validPanelId);
        assertNotNull(settings);
    }

}
