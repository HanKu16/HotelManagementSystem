package org.po2_jmp.Panels;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PanelIdTest {

    @Test
    void Constructor_ShouldThrowException_WhenPanelIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PanelId(null);
        });
    }

    @Test
    void Constructor_ShouldThrowException_WhenPanelIdIsOnlyWhitespace() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PanelId("   ");
        });
    }

    @Test
    void Constructor_ShouldThrowException_WhenPanelIdIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PanelId("");
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenPanelIdValid() {
        PanelId panelId = new PanelId("LoginPanel");
        assertNotNull(panelId);
    }

    @Test
    void GetValue_ShouldReturnValue() {
        PanelId panelId = new PanelId("LoginPanel");
        assertEquals("LoginPanel", panelId.getValue());
    }

}