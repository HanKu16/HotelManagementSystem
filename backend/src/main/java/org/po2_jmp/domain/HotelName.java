package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class HotelName {

    private final static int MIN_LENGTH = 2;
    private final static int MAX_LENGTH = 50;
    private final String value;

    public HotelName(String hotelName) {
        if (isNullOrBlank(hotelName)) {
            throw new IllegalArgumentException(
                    "HotelName can not be null or blank");
        }
        if (!isLengthInRange(hotelName)) {
            throw new IllegalArgumentException("HotelName must contain " +
                    MIN_LENGTH + " to " + MAX_LENGTH + " characters");
        }
        if (!containsOnlyLettersAndWhiteSpaces(hotelName)) {
            throw new IllegalArgumentException("HotelName can only contain " +
                    "letters and white spaces");
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
