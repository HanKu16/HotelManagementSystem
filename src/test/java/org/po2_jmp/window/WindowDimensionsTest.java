package org.po2_jmp.window;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WindowDimensionsTest {

    private final int tooSmallWidth = 0;
    private final int tooBigWidth = 3000;
    private final int tooSmallHeight = -20;
    private final int tooBigHeight = 1500;
    private final int validWidth = 1000;
    private final int validHeight = 600;

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenWidthIsSmallerThanAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowDimensions(tooSmallWidth, validHeight);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenWidthIsGreaterThanAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowDimensions(tooBigWidth, validHeight);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenHeightIsSmallerThanAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowDimensions(validWidth, tooSmallHeight);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenWidthIsSmallerThan1() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowDimensions(validWidth, tooBigHeight);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenArgumentsAreValid() {
        WindowDimensions dimensions = new WindowDimensions(validWidth, validHeight);
        assertNotNull(dimensions);
    }

}
