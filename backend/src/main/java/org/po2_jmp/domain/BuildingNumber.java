package org.po2_jmp.domain;

import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class BuildingNumber {

    private final String value;

    public BuildingNumber(String buildingNumber) {
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
