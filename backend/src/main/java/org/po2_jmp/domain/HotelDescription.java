package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class HotelDescription {

    private final static int MAX_LENGTH = 1000;
    private final String value;

    public HotelDescription(String description) {
        if (isNull(description)) {
            throw new IllegalArgumentException(
                    "Hotel Description can not be null");
        }
        if (!isLengthInRange(description)) {
            throw new IllegalArgumentException("HotelDescription can not " +
                    "be longer than " + MAX_LENGTH);
        }
        this.value = description;
    }

    public boolean isNull(String description) {
        return description == null;
    }

    private boolean isLengthInRange(String description) {
        return description.length() <= MAX_LENGTH;
    }

}
