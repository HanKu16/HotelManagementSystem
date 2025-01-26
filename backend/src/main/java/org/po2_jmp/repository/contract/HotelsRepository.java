package org.po2_jmp.repository.contract;

import org.po2_jmp.entity.Hotel;
import java.util.List;
import java.util.Optional;

/**
 * Represents a repository interface for managing {@link Hotel} entities.
 * <p>
 * The {@link HotelsRepository} interface defines the contract for accessing and
 * modifying hotel information in a data storage system. This interface provides methods
 * to retrieve hotels by their identifier, find all hotels, and add new hotels to the system.
 * </p>
 */
public interface HotelsRepository {

    /**
     * Finds a hotel by its unique identifier.
     *
     * @param id the unique identifier of the hotel
     * @return an {@link Optional} containing the {@link Hotel} if found, or an empty
     *         {@link Optional} if no hotel with the given id exists
     */
    Optional<Hotel> findById(int id);

    /**
     * Finds all hotels in the system.
     *
     * @return a {@link List} of all {@link Hotel} objects
     */
    List<Hotel> findAll();

    /**
     * Adds a new hotel to the system.
     *
     * @param hotel the {@link Hotel} to be added
     * @return an {@link Optional} containing the id of the newly added hotel
     *        if the addition is successful, or an empty {@link Optional} if the
     *        hotel could not be added
     */
    Optional<Integer> add(Hotel hotel);

}
