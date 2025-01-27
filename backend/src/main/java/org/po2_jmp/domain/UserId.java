package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a user ID (login) with validation checks.
 * <p>
 * The {@link UserId} class ensures that the provided user ID (login)
 * is valid by checking the following:
 * <ul>
 *   <li>The ID must not be null or blank</li>
 *   <li>The ID must have a length between {@link #MIN_LENGTH}
 *      and {@link #MAX_LENGTH}</li>
 *   <li>The ID can only contain letters and numbers</li>
 * </ul>
 */
@Getter
@EqualsAndHashCode
public class UserId {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 16;
    private final String value;

    /**
     * Constructs a {@link UserId} with the specified user login.
     * <p>
     * Validates that the login is not null or blank, its length
     * is within the specified range, and it contains
     * only letters and numbers.
     *
     * @param login the user ID (login) to be validated
     * @throws IllegalArgumentException if the login is null,
     *         blank, contains invalid characters,
     *         or its length is outside the specified range
     */
    public UserId(String login) {
        if (isNullOrBlank(login)) {
            throw new IllegalArgumentException("user login can not be null or blank");
        }
        if (!isLengthInRange(login)) {
            throw new IllegalArgumentException("user login must contain " +
                    MIN_LENGTH + " to " + MAX_LENGTH + " characters, but " +
                    login + " contains " + login.length());
        }
        if (!containsOnlyLettersAndNumbers(login)) {
            throw new IllegalArgumentException("user login can contain " +
                    "only letters and numbers, but is " + login);
        }
        this.value = login;
    }

    private boolean isNullOrBlank(String userId) {
        return (userId == null) || userId.isBlank();
    }

    private boolean isLengthInRange(String userId) {
        return (userId.length() >= MIN_LENGTH) && (userId.length() <= MAX_LENGTH);
    }

    private boolean containsOnlyLettersAndNumbers(String userId) {
        return userId.matches("^[a-zA-Z0-9]+$");
    }

}
