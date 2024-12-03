package org.po2_jmp.AppWindow;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WindowDimensions {

    private static final int MIN_WIDTH = 1;
    private static final int MAX_WIDTH = 1920;
    private static final int MIN_HEIGHT = 1;
    private static final int MAX_HEIGHT = 1080;
    private final int width;
    private final int height;

    public WindowDimensions(int width, int height) {
        if (!isWidthValid(width) || !isHeightValid(height)) {
            throw new IllegalArgumentException("Width must be value in " +
                    "between <" + MIN_WIDTH + ", " + MAX_WIDTH +
                    ">\nHeight must be value in <" + MIN_HEIGHT +
                    ", " + MAX_HEIGHT + ">");
        }
        this.width = width;
        this.height = height;
    }

    private boolean isWidthValid(int width) {
        return (width >= MIN_WIDTH) && (width <= MAX_WIDTH);
    }

    private boolean isHeightValid(int height) {
        return (height >= MIN_HEIGHT) && (height <= MAX_HEIGHT);
    }

}