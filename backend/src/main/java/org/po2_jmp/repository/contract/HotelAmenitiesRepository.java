package org.po2_jmp.repository.contract;

import java.util.List;
import java.util.Optional;
import org.po2_jmp.entity.HotelAmenity;

/**
 * Represents a repository interface for managing {@link HotelAmenity} entities.
 * <p>
 * The {@link HotelAmenitiesRepository} interface defines the contract for accessing and
 * modifying hotel amenities in a data storage system. This interface provides methods
 * to retrieve hotel amenities by their identifier, find all amenities for a specific hotel,
 * and add new amenities to the system.
 */
public interface HotelAmenitiesRepository {

    /**
     * Finds a hotel amenity by its unique identifier.
     *
     * @param id the unique identifier of the hotel amenity
     * @return an {@link Optional} containing the {@link HotelAmenity} if found,
     *        or an empty {@link Optional} if no amenity with the given id exists
     */
    Optional<HotelAmenity> findById(int id);

    /**
     * Finds all hotel amenities associated with a specific hotel.
     *
     * @param hotelId the unique identifier of the hotel
     * @return a {@link List} of {@link HotelAmenity} objects
     *         associated with the given hotel id
     */
    List<HotelAmenity> findAllByHotelId(int hotelId);

    /**
     * Adds a new hotel amenity to the system.
     *
     * @param amenity the {@link HotelAmenity} to be added
     * @return an {@link Optional} containing the id of the newly
     *        added amenity if the addition is successful, or
     *        an empty {@link Optional} if the amenity could not be added
     */
    Optional<Integer> add(HotelAmenity amenity);

}
