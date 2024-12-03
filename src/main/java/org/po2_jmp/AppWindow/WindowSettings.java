package org.po2_jmp.AppWindow;

import lombok.Getter;

@Getter
public class WindowSettings {

    private final WindowDimensions dimensions;
    private final WindowTitle title;

    public WindowSettings(
            WindowDimensions dimensions,
            WindowTitle title) {
        this.dimensions = dimensions;
        this.title = title;
    }

}

