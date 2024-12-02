package org.po2_jmp.AppWindow;

import lombok.Getter;

@Getter
public class WindowDimensions {

    private static final int MIN_WIDTH = 1;
    private static final int MAX_WIDTH = 1920;
    private static final int MIN_HEIGHT = 1;
    private static final int MAX_HEIGHT = 1080;

    private final int width;
    private final int height;

    public WindowDimensions(int width, int height) {
        validateDimensions(width, height);
        this.width = width;
        this.height = height;
    }

    private void validateDimensions(int width, int height) {
        validateWidth(width);
        validateHeight(height);
    }

    private void validateWidth(int width) {
        if ((width < MIN_WIDTH) || (width > MAX_WIDTH)) {
            throw new IllegalArgumentException("Width must be " +
                    "between " + MIN_WIDTH + " and " +
                    MAX_WIDTH + " but is " + width);

        }
    }

    private void validateHeight(int height) {
        if ((height < MIN_HEIGHT) || (height > MAX_HEIGHT)) {
            throw new IllegalArgumentException("Height must be " +
                    "between " + MIN_HEIGHT + " and " +
                    MAX_HEIGHT + " but is " + height);
        }
    }

}
