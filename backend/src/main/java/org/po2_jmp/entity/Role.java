package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.RoleName;

/**
 * Represents a role entity.
 * <p>
 * The {@link Role} class encapsulates the details of a role,
 * including its unique identifier and the name of the role.
 * It ensures that the role name is not null when creating a role instance.
 * </p>
 */
@Getter
public class Role {

    private final int id;
    private final RoleName name;

    /**
     * Constructs a {@link Role} with the specified id and role name.
     * <p>
     * This constructor throws an {@link IllegalArgumentException}
     * if the role name is null.
     * </p>
     *
     * @param id the unique identifier for the role
     * @param name the name of the role
     *
     * @throws IllegalArgumentException if the role name is null
     */
    public Role(int id, RoleName name) {
        if (name == null) {
            throw new IllegalArgumentException("RoleName can not be null," +
                    "but null was passed to Role constructor");
        }
        this.id = id;
        this.name = name;
    }

}
