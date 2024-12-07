package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CityName {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 60;
    private final String value;

    public CityName(String cityName) {
        if (isNullOrBlank(cityName)) {
            throw new IllegalArgumentException("City name can not be + " +
                    "null or empty but is " + cityName);
        }
        if (!isInRange(cityName)) {
            throw new IllegalArgumentException("City name must contain between " +
                    MIN_LENGTH + " to " + MAX_LENGTH + " characters, but " +
                    cityName + " has " + cityName.length());
        }
        if (!containsOnlyLettersAndWhiteSpaces(cityName)) {
            throw new IllegalArgumentException("City name can contain only valid " +
                    "polish letters and white spaces, but is " + cityName);
        }
        this.value = cityName;
    }

    private boolean isNullOrBlank(String cityName) {
        return (cityName == null) || cityName.isBlank();
    }

    private boolean isInRange(String cityName) {
        return (cityName.length() >= MIN_LENGTH) &&
                (cityName.length() <= MAX_LENGTH);
    }

    private boolean containsOnlyLettersAndWhiteSpaces(String cityName) {
        String regex = "^[A-Za-zĄąĆćĘęŁłŃńÓóŚśŹźŻż]+" +
                "(?:[ -][A-Za-zĄąĆćĘęŁłŃńÓóŚśŹźŻż]+)*$";
        return cityName.matches(regex);
    }

}
