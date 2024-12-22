package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserPassword {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 20;
    private final String value;

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

