package org.po2_jmp.AppWindow;

import lombok.Getter;

@Getter
public class WindowSettings {

    private final WindowDimensions dimensions;
    private final WindowTitle title;
    private final String startPanelId;

    public WindowSettings(WindowDimensions dimensions,
                          WindowTitle title, String startPanelId) {
        if (!areWindowDimensionsValid(dimensions) || !isTitleValid(title)) {
            throw new IllegalArgumentException("Dimensions and title can not " +
                    "be null but " + dimensions + " are " + dimensions +
                    " and " + title + " is " + title);
        }
        if (!isStartPanelIdValid(startPanelId)) {
            throw new IllegalArgumentException("StartPanelId can not be null " +
                    "but is " + startPanelId);
        }
        this.dimensions = dimensions;
        this.title = title;
        this.startPanelId = startPanelId;
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

