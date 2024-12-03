package org.po2_jmp.AppWindow;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WindowSettings {

    private final WindowDimensions dimensions;
    private final WindowTitle title;
    private final String startPanelId;

    public WindowSettings(WindowDimensions dimensions,
                          WindowTitle title, String startPanelId) {
        if (!areParamsValid(dimensions, title, startPanelId)) {
            throw new IllegalArgumentException("Illegal values passed " +
                    "to constructor");
        }
        this.dimensions = dimensions;
        this.title = title;
        this.startPanelId = startPanelId;
    }

    private boolean areParamsValid(WindowDimensions dimensions,
            WindowTitle title, String startPanelId) {
        return areWindowDimensionsValid(dimensions) && isTitleValid(title) &&
                isStartPanelIdValid(startPanelId);
    }

    private boolean areWindowDimensionsValid(WindowDimensions dimensions) {
        return dimensions != null;
    }

    private boolean isTitleValid(WindowTitle title) {
        return title != null;
    }

    private boolean isStartPanelIdValid(String panelId) {
        return panelId != null;
    }

}

