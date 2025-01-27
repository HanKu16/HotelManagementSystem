package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents the name of a hotel with validation for length and content.
 * <p>
 * A valid hotel name must meet the following criteria:
 * <ul>
 *     <li>Cannot be null or blank.</li>
 *     <li>Must contain between {@link #MIN_LENGTH} and {@link #MAX_LENGTH}
 *          characters (inclusive).</li>
 *     <li>Can only contain letters and white spaces.</li>
 * </ul>
 * <p>
 * If any of these conditions are violated, an {@link IllegalArgumentException} is thrown.
 */
@Getter
@EqualsAndHashCode
public class HotelName {

    private final static int MIN_LENGTH = 2;
    private final static int MAX_LENGTH = 50;
    private final String value;

    /**
     * Constructs a {@link HotelName} object with the given hotel name.
     * <p>
     * If the provided hotel name is invalid (null, blank, not within length
     * range, or containing invalid characters), an {@link IllegalArgumentException}
     * will be thrown.
     *
     * @param hotelName the hotel name to be validated and assigned
     * @throws IllegalArgumentException if the hotel name is null, blank,
     *        out of range, or contains invalid characters
     */
    public HotelName(String hotelName) {
        if (isNullOrBlank(hotelName)) {
            throw new IllegalArgumentException("Hotel name can not be " +
                    "null or blank");
        }
        if (!isLengthInRange(hotelName)) {
            throw new IllegalArgumentException("Hotel name must contain " +
                    MIN_LENGTH + " to " + MAX_LENGTH + " characters, " +
                    "but " + hotelName + " contains " + hotelName.length());
        }
        if (!containsOnlyLettersAndWhiteSpaces(hotelName)) {
            throw new IllegalArgumentException("Hotel name can only contain " +
                    "letters and white spaces, but is" + hotelName);
        }
        this.value = hotelName;
    }

    private boolean isNullOrBlank(String hotelName) {
        return (hotelName == null) || hotelName.isBlank();
    }

    private boolean isLengthInRange(String hotelName) {
        return (hotelName.length() >= MIN_LENGTH) &&
                (hotelName.length() <= MAX_LENGTH);
    }

    private boolean containsOnlyLettersAndWhiteSpaces(String hotelName) {
        return hotelName.matches("^[a-zA-Z ]+$");
    }

}
