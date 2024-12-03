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
            throw new IllegalArgumentException("Title can not be null and " +
                    "longer than " + MAX_SIZE);
        }
        this.value = title;
    }

    private boolean isTitleValid(String title) {
        return (title != null) && (title.length() <= MAX_SIZE);
    }

}
