package org.po2_jmp.AppWindow;

import lombok.Getter;

@Getter
public class WindowSettings {

    private final WindowDimensions dimensions;
    private final WindowTitle title;

    public WindowSettings(WindowDimensions dimensions, WindowTitle title) {
        if (!areWindowDimensionsValid(dimensions) || !isTitleValid(title)) {
            throw new IllegalArgumentException("Dimensions and title can not " +
                    "be null but " + dimensions + " are " + dimensions +
                    " and " + title + " is " + title);
        }
        this.dimensions = dimensions;
        this.title = title;
    }

    public boolean areWindowDimensionsValid(WindowDimensions dimensions) {
        return dimensions != null;
    }

    public boolean isTitleValid(WindowTitle title) {
        return title != null;
    }

}

