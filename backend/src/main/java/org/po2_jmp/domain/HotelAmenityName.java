package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class HotelAmenityName {

    private final static int MIN_LENGTH = 2;
    private final static int MAX_LENGTH = 50;
    private final String value;

    public HotelAmenityName(String name) {
        if (isNullOrBlank(name)) {
            throw new IllegalArgumentException("Hotel amenity name can " +
                    "not be null or blank");
        }
        if (!isLengthInRange(name)) {
            throw new IllegalArgumentException("Hotel amenity name must contain " +
                    MIN_LENGTH + " to " + MAX_LENGTH + " characters, but " +
                    "contains " + name.length());
        }
        this.value = name;
    }

    private boolean isNullOrBlank(String name) {
        return (name == null) || name.isBlank();
    }

    private boolean isLengthInRange(String name) {
        return (name.length() >= MIN_LENGTH) &&
                (name.length() <= MAX_LENGTH);
    }

}
