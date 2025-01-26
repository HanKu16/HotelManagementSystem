package org.po2_jmp.repository.contract;

import org.po2_jmp.entity.Role;
import java.util.Optional;

/**
 * Represents a repository interface for managing {@link Role} entities.
 * <p>
 * The {@link RolesRepository} interface defines the contract for accessing and
 * retrieving role information from a data storage system. This interface provides
 * methods to retrieve a role by its unique identifier.
 * </p>
 */
public interface RolesRepository {

    /**
     * Finds a role by its unique identifier.
     *
     * @param id the unique identifier of the role
     * @return an {@link Optional} containing the {@link Role} if found, or an empty
     *         {@link Optional} if no role with the given id exists
     */
    Optional<Role> findById(int id);

}
