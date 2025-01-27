package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import java.util.regex.Pattern;
import lombok.Getter;

/**
 * Represents a postal code in Poland with validation for format.
 * <p>
 * A valid postal code for Poland must follow the format XX-XXX, where:
 * <ul>
 *     <li>XX represents two digits (0-9)</li>
 *     <li>XXX represents three digits (0-9)</li>
 * </ul>
 * <p>
 * If the provided postal code is null or does not match the expected format,
 * an {@link IllegalArgumentException} is thrown.
 */
@Getter
@EqualsAndHashCode
public class PostalCode {

    private final String value;

    /**
     * Constructs a {@link PostalCode} object with the given postal code.
     * <p>
     * If the provided postal code is null or does not match the format XX-XXX
     * (where each X is a digit), an {@link IllegalArgumentException} is thrown.
     *
     * @param postalCode the postal code to be validated and assigned
     * @throws IllegalArgumentException if the postal code is null or
     * has an invalid format
     */
    public PostalCode(String postalCode) {
        if (postalCode == null) {
            throw new IllegalArgumentException(
                    "Postal code can not be null");
        }
        if (!isValid(postalCode)) {
            throw new IllegalArgumentException(postalCode + " is invalid for " +
                    "Poland were format is XX-XXX, where each X has to be digit " +
                    "scoped from 0 to 9");
        }
        this.value = postalCode;
    }

    private boolean isValid(String postalCode) {
        return Pattern.matches("\\d{2}-\\d{3}", postalCode);
    }

}

