package org.po2_jmp.AppWindow;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WindowTitle {

    private static final int MAX_SIZE = 50;
    private final String value;

    public WindowTitle(String title) {
        if (!isTitleValid(title)) {
            throw new IllegalArgumentException("Illegal values passed " +
                    "to constructor");
        }
        this.value = title;
    }

    private boolean isTitleValid(String title) {
        return (title != null) && (title.length() <= MAX_SIZE);
    }

}
