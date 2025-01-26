package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents the description of a hotel with validation for length.
 * <p>
 * A valid hotel description must meet the following criteria:
 * <ul>
 *     <li>Cannot be null.</li>
 *     <li>Must not exceed {@link #MAX_LENGTH} characters in length
 *          (1000 characters).</li>
 * </ul>
 * </p>
 * <p>
 * If any of these conditions are violated, an {@link IllegalArgumentException} is thrown.
 * </p>
 */
@Getter
@EqualsAndHashCode
public class HotelDescription {

    private final static int MAX_LENGTH = 1000;
    private final String value;

    /**
     * Constructs a {@link HotelDescription} object with the given description.
     * <p>
     * If the provided description is invalid (null or exceeds the maximum length),
     * an {@link IllegalArgumentException} will be thrown.
     * </p>
     *
     * @param description the hotel description to be validated and assigned
     * @throws IllegalArgumentException if the description is null or
     *         exceeds the maximum length
     */
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
