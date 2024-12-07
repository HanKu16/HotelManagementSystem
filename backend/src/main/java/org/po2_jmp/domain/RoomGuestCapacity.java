package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class RoomGuestCapacity {

    private final static int MIN_VALUE = 1;
    private final static int MAX_VALUE = 5;
    private final int value;

    public RoomGuestCapacity(int capacity) {
        if (!isInRange(capacity)) {
            throw new IllegalArgumentException("Room guest capacity must be " +
                    "between " + MIN_VALUE + " and " + MAX_VALUE);
        }
        this.value = capacity;
    }

    private boolean isInRange(int capacity) {
        return (capacity >= MIN_VALUE) && (capacity <= MAX_VALUE);
    }

}
