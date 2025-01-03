package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class HotelDescription {

    private final static int MAX_LENGTH = 1000;
    private final String value;

    public HotelDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Hotel description can " +
                    "not be null");
        }
        if (!isLengthInRange(description)) {
            throw new IllegalArgumentException("Hotel description can not " +
                    "be longer than " + MAX_LENGTH + ", but (" + description +
                    ") is " + description.length() + "characters long");
        }
        this.value = description;
    }

    private boolean isLengthInRange(String description) {
        return description.length() <= MAX_LENGTH;
    }

}
