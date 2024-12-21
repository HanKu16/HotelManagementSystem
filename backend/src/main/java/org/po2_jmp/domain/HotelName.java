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
