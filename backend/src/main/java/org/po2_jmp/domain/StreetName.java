package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class StreetName {

    private final static int MAX_LENGTH = 100;
    private final String value;

    public StreetName(String street) {
        if (street == null || street.isEmpty()) {
            throw new IllegalArgumentException("Street name can " +
                    "not be null or empty");
        }
        if (!isLengthInRange(street)) {
            throw new IllegalArgumentException("Hotel description can not " +
                    "be longer than " + MAX_LENGTH + ", but (" + street +
                    ") is " + street.length() + "characters long");
        }
        this.value = street;
    }

    private boolean isLengthInRange(String street) {
        return street.length() <= MAX_LENGTH;
    }

}


