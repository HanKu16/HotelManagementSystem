package org.po2_jmp.repository.contract;

import org.po2_jmp.entity.HotelRoom;
import java.util.List;
import java.util.Optional;

/**
 * Represents a repository interface for managing {@link HotelRoom} entities.
 * <p>
 * The {@link HotelRoomsRepository} interface defines the contract for accessing and
 * modifying hotel room information in a data storage system. This interface
 * provides methods to retrieve hotel rooms by their identifier, find rooms for
 * a specific hotel and its guest capacity, and add new rooms to the system.
 */
public interface HotelRoomsRepository {

    /**
     * Finds a hotel room by its unique identifier.
     *
     * @param id the unique identifier of the hotel room
     * @return an {@link Optional} containing the {@link HotelRoom} if found,
     *       or an empty {@link Optional} if no room with the given id exists
     */
    Optional<HotelRoom> findById(int id);

    /**
     * Finds all hotel rooms associated with a specific hotel.
     *
     * @param hotelId the unique identifier of the hotel
     * @return a {@link List} of {@link HotelRoom} objects associated
     *         with the given hotel id
     */
    List<HotelRoom> findAllByHotelId(int hotelId);

    /**
     * Finds all hotel rooms associated with a specific hotel and matching
     * a specific guest capacity.
     *
     * @param hotelId the unique identifier of the hotel
     * @param guestCapacity the required guest capacity of the rooms
     * @return a {@link List} of {@link HotelRoom} objects matching the
     *         given hotel id and guest capacity
     */
    List<HotelRoom> findAllByHotelIdAndGuestCapacity(int hotelId, int guestCapacity);

    /**
     * Adds a new hotel room to the system.
     *
     * @param room the {@link HotelRoom} to be added
     * @return an {@link Optional} containing the id of the newly added
     *        room if the addition is successful, or an empty {@link Optional}
     *        if the room could not be added
     */
    Optional<Integer> add(HotelRoom room);

}
