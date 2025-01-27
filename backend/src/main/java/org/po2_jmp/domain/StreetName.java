package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents the name of a street.
 * <p>
 * The {@link StreetName} class validates that the provided
 * street name is neither null nor empty and that its length
 * does not exceed a maximum allowable length.
 */
@Getter
@EqualsAndHashCode
public class StreetName {

    private final static int MAX_LENGTH = 100;
    private final String value;

    /**
     * Constructs a {@link StreetName} with the specified street name.
     * <p>
     * Validates that the provided street name is neither null nor empty,
     * and that its length is within the allowable limit.
     *
     * @param street the name of the street
     * @throws IllegalArgumentException if the street name is null,
     *        empty, or its length exceeds {@link #MAX_LENGTH}
     */
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


