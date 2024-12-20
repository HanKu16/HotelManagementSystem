package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserId {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 16;
    private final String value;

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
