package org.po2_jmp.domain;

import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a building number which is validated against a specific pattern.
 * <p>
 * A valid building number must consist of one or more digits, optionally
 * followed by a letter (e.g., "A"), and optionally followed by a slash
 * ("/") and more digits (e.g., "/123").
 * <p>
 * This class ensures that the building number adheres to the expected format.
 * If the provided building number is invalid or null, an
 * {@link IllegalArgumentException} will be thrown.
 * <p>
 * The validation pattern follows the regex: <code>\\d+([A-Za-z])?(\\/\\d+)?</code>
 */
@Getter
@EqualsAndHashCode
public class BuildingNumber {

    private final String value;

    /**
     * Constructs a {@link BuildingNumber} with the given building number.
     * <p>
     * If the provided building number is {@code null} or doesn't match the valid pattern,
     * an {@link IllegalArgumentException} will be thrown.
     *
     * @param buildingNumber the building number to be validated and assigned
     * @throws IllegalArgumentException if the building number is
     *         {@code null} or does not match the valid pattern
     */
    public BuildingNumber(String buildingNumber) {
        if (buildingNumber == null) {
            throw new IllegalArgumentException(
                    "Building number can not be null");
        }
        if (!isValid(buildingNumber)) {
            throw new IllegalArgumentException(buildingNumber +
                    " is invalid building number");
        }
        this.value = buildingNumber;
    }

    private boolean isValid(String buildingNumber) {
        return Pattern.matches("\\d+([A-Za-z])?(\\/\\d+)?", buildingNumber);
    }

}
