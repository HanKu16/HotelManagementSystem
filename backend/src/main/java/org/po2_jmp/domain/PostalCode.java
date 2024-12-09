package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import java.util.regex.Pattern;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PostalCode {

    private final String value;

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

