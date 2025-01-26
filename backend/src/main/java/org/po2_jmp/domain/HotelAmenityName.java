package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents the name of a hotel amenityn.
 * <p>
 * A valid hotel amenity name must meet the following criteria:
 * <ul>
 *     <li>Cannot be null or blank.</li>
 *     <li>Must contain between 2 and 50 characters (inclusive).</li>
 * </ul>
 * </p>
 * <p>
 * If any of these conditions are violated, an {@link IllegalArgumentException} is thrown.
 * </p>
 */
@Getter
@EqualsAndHashCode
public class HotelAmenityName {

    private final static int MIN_LENGTH = 2;
    private final static int MAX_LENGTH = 50;
    private final String value;

    /**
     * Constructs a {@link HotelAmenityName} object with the given name.
     * <p>
     * If the provided name is invalid (null, blank, or out of range in length),
     * an {@link IllegalArgumentException} will be thrown.
     * </p>
     *
     * @param name the hotel amenity name to be validated and assigned
     * @throws IllegalArgumentException if the name is null, blank,
     *        or has an invalid length
     */
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
