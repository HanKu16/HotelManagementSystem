package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserLogin {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 16;
    private final String value;

    public UserLogin(String login) {
        if (isNullOrBlank(login)) {
            throw new IllegalArgumentException("User login can not be null or blank");
        }
        if (!isLengthInRange(login)) {
            throw new IllegalArgumentException("User login must contain " +
                    MIN_LENGTH + " to " + MAX_LENGTH + " characters, but " +
                    login + " contains " + login.length());
        }
        if (!containsOnlyLettersAndNumbers(login)) {
            throw new IllegalArgumentException("User login can contain " +
                    "only letters and numbers, but is " + login);
        }
        this.value = login;
    }

    private boolean isNullOrBlank(String login) {
        return (login == null) || login.isBlank();
    }

    private boolean isLengthInRange(String login) {
        return (login.length() >= MIN_LENGTH) && (login.length() <= MAX_LENGTH);
    }

    private boolean containsOnlyLettersAndNumbers(String login) {
        return login.matches("^[a-zA-Z0-9]+$");
    }

}
