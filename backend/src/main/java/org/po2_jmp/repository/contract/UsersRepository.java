package org.po2_jmp.repository.contract;

import org.po2_jmp.entity.User;
import java.util.Optional;

/**
 * Represents a repository interface for managing {@link User} entities.
 * <p>
 * The {@link UsersRepository} interface defines the contract for accessing and
 * managing user information in a data storage system. This interface provides
 * methods to find a user by their unique identifier and to add a new user to the repository.
 * </p>
 */
public interface UsersRepository {

    /**
     * Finds a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return an {@link Optional} containing the {@link User} if found, or an empty
     *         {@link Optional} if no user with the given ID exists
     */
    Optional<User> findById(String id);

    /**
     * Adds a new user to the repository.
     *
     * @param user the user to be added
     * @return an {@link Optional} containing the unique identifier (ID) of the added user,
     *         or an empty {@link Optional} if the user could not be added
     */
    Optional<String> add(User user);

}
