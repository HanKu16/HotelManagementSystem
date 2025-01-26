package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a user password with validation checks.
 * <p>
 * The {@link UserPassword} class ensures that the provided
 * password is valid by checking the following:
 * <ul>
 *   <li>The password must not be null or blank</li>
 *   <li>The password must have a length between {@link #MIN_LENGTH}
 *      and {@link #MAX_LENGTH}</li>
 *   <li>The password can only contain letters and numbers</li>
 * </ul>
 * </p>
 */
@Getter
@EqualsAndHashCode
public class UserPassword {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 20;
    private final String value;

    /**
     * Constructs a {@link UserPassword} with the specified password.
     * <p>
     * Validates that the password is not null or blank, its length
     * is within the specified range, and it contains
     * only letters and numbers.
     * </p>
     *
     * @param password the password to be validated
     * @throws IllegalArgumentException if the password is null,
     *         blank, contains invalid characters, or its length
     *         is outside the specified range
     */
    public UserPassword(String password) {
        if (isNullOrBlank(password)) {
            throw new IllegalArgumentException(
                    "User password can not be null or blank");
        }
        if (!isLengthInRange(password)) {
            throw new IllegalArgumentException("User password must contain " +
                    MIN_LENGTH + " to " + MAX_LENGTH + " characters, but " +
                    "contains " + password.length());
        }
        if (!containsOnlyLettersAndNumbers(password)) {
            throw new IllegalArgumentException("User password can contain " +
                    "only letters and numbers, but is " + password);
        }
        this.value = password;
    }

    private boolean isNullOrBlank(String password) {
        return (password == null) || password.isBlank();
    }

    private boolean isLengthInRange(String password) {
        return (password.length() >= MIN_LENGTH) &&
                (password.length() <= MAX_LENGTH);
    }

    private boolean containsOnlyLettersAndNumbers(String password) {
        return password.matches("^[a-zA-Z0-9]+$");
    }

}

