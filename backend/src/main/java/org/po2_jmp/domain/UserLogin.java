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
            throw new IllegalArgumentException(
                    "Login can not be null or blank");
        }
        if (!isLengthInRange(login)) {
            throw new IllegalArgumentException("Login must contain " +
                    MIN_LENGTH + " to " + MAX_LENGTH + " characters");
        }
        if (!containsOnlyLettersAndNumbers(login)) {
            throw new IllegalArgumentException("Login can contain " +
                    "only letters and numbers");
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
