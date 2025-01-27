package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a city name with validation for format, length, and allowed characters.
 * <p>
 * A valid city name must meet the following criteria:
 * <ul>
 *     <li>Cannot be null or empty.</li>
 *     <li>Must be between 2 and 60 characters in length.</li>
 *     <li>Can only contain Polish letters (both uppercase and
 *     lowercase), spaces, and hyphens.</li>
 * </ul>
 * <p>
 * If any of these conditions are violated, an {@link IllegalArgumentException} is thrown.
 */
@Getter
@EqualsAndHashCode
public class CityName {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 60;
    private final String value;

    /**
     * Constructs a {@link CityName} object with the given city name.
     * <p>
     * If the provided city name is invalid, an
     * {@link IllegalArgumentException} will be thrown.
     *
     * @param cityName the city name to be validated and assigned
     * @throws IllegalArgumentException if the city name is null,
     *      empty, too short, too long, or contains invalid characters
     */
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
