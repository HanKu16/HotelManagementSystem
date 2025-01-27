package org.po2_jmp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents the capacity of guests a room can accommodate.
 * <p>
 * The {@link RoomGuestCapacity} class validates that the provided
 * capacity is within the allowable range (1 to 5 guests)
 */
@Getter
@EqualsAndHashCode
public class RoomGuestCapacity {

    private final static int MIN_VALUE = 1;
    private final static int MAX_VALUE = 5;
    private final int value;

    /**
     * Constructs a {@link RoomGuestCapacity} with the specified capacity.
     * <p>
     * Validates that the provided capacity is between the minimum and
     * maximum allowable values.
     *
     * @param capacity the number of guests the room can accommodate
     * @throws IllegalArgumentException if the capacity is less
     *      than {@link #MIN_VALUE} or greater than {@link #MAX_VALUE}
     */
    public RoomGuestCapacity(int capacity) {
        if (!isInRange(capacity)) {
            throw new IllegalArgumentException("Room guest capacity must be " +
                    "between " + MIN_VALUE + " and " + MAX_VALUE +
                    " ,but is " + capacity);
        }
        this.value = capacity;
    }

    private boolean isInRange(int capacity) {
        return (capacity >= MIN_VALUE) && (capacity <= MAX_VALUE);
    }

}
