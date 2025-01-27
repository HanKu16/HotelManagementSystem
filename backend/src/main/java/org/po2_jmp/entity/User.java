package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.*;
import java.time.LocalDateTime;

/**
 * Represents a user entity.
 * <p>
 * The {@link User} class encapsulates the details of a user,
 * including the user's unique identifier, password, creation date and time,
 * and associated role. It ensures that none of the critical attributes
 * (id, password, and creation date) are null during object construction.
 */
@Getter
public class User {

    private final UserId id;
    private final UserPassword password;
    private final LocalDateTime creationDateTime;
    private final int roleId;

    /**
     * Constructs a {@link User} with the specified id, password, and roleId.
     * <p>
     * This constructor automatically sets the creation date and time to the
     * current date and time using {@link LocalDateTime#now()}.
     *
     * @param id the unique identifier for the user
     * @param password the user's password
     * @param roleId the role identifier for the user
     *
     * @throws IllegalArgumentException if the user id, password
     *        or creation date is null
     */
    public User(UserId id, UserPassword password, int roleId) {
        this(id, password, LocalDateTime.now(), roleId);
    }

    /**
     * Constructs a {@link User} with the specified id, password,
     * creation date and time, and roleId.
     * <p>
     * This constructor ensures that none of the critical parameters
     * (id, password, and creation date) are null by throwing an
     * {@link IllegalArgumentException} if any of them are.
     *
     * @param id the unique identifier for the user
     * @param password the user's password
     * @param creationDateTime the creation date and time of the user
     * @param roleId the role identifier for the user
     *
     * @throws IllegalArgumentException if the user id, password,
     *         or creation date is null
     */
    public User(UserId id, UserPassword password,
                LocalDateTime creationDateTime, int roleId) {
        if (areAnyNullParams(id, password, creationDateTime)) {
            throw new IllegalArgumentException("User id, password and " +
                    "creation datetime can not be nulls, but null was " +
                    "passed to User constructor");
        }
        this.id = id;
        this.password = password;
        this.creationDateTime = creationDateTime;
        this.roleId = roleId;
    }

    private boolean areAnyNullParams(UserId id, UserPassword password,
            LocalDateTime creationDateTime) {
        return (id == null) || (password == null) ||
                (creationDateTime == null);
    }

}