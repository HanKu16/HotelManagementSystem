package org.po2_jmp.repository.implementation;

import org.po2_jmp.domain.RoomGuestCapacity;
import org.po2_jmp.entity.HotelRoom;
import org.po2_jmp.repository.contract.HotelRoomsRepository;
import org.po2_jmp.repository.helper.DbUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link HotelRoomsRepository} interface using a relational database.
 * This class provides CRUD operations for hotel rooms using SQL queries.
 * <p>
 * The methods use the {@link DbUtils} utility class to execute SQL queries and handle
 * database interactions. The implementation assumes that a database connection is
 * managed through the {@link DbUtils} and SQL queries are executed accordingly.
 * </p>
 */
public class DbHotelRoomsRepository implements HotelRoomsRepository {

    private final DbUtils dbUtils;

    /**
     * Constructs a {@link DbHotelRoomsRepository} with the specified {@link DbUtils} object.
     *
     * @param dbUtils the {@link DbUtils} utility for executing SQL queries
     * @throws IllegalArgumentException if {@code dbUtils} is {@code null}
     */
    public DbHotelRoomsRepository(DbUtils dbUtils) {
        if (dbUtils == null) {
            throw new IllegalArgumentException("DbUtils can not be null but " +
                    "null was passed to DbHotelRoomsRepository constructor");
        }
        this.dbUtils = dbUtils;
    }

    /**
     * Finds a hotel room by its unique ID.
     *
     * @param id the ID of the hotel room to find
     * @return an {@link Optional} containing the {@link HotelRoom} if found, or
     * {@link Optional#empty()} if not
     */
    @Override
    public Optional<HotelRoom> findById(int id) {
        String sql = "SELECT hotel_room_id, guest_capacity, hotel_id" +
                    " FROM hotel_rooms" +
                    " WHERE hotel_room_id = ?;";
        return dbUtils.executeQuery(sql,
                stmt -> stmt.setInt(1, id),
                rs -> createRoom(rs));
    }

    /**
     * Finds all hotel rooms associated with a specific hotel ID.
     *
     * @param hotelId the ID of the hotel whose rooms to find
     * @return a {@link List} of {@link HotelRoom} associated with the hotel
     */
    @Override
    public List<HotelRoom> findAllByHotelId(int hotelId) {
        String sql = "SELECT hotel_room_id, guest_capacity, hotel_id" +
                    " FROM hotel_rooms" +
                    " WHERE hotel_id = ?;";
        return dbUtils.executeQueryForCollection(sql,
                stmt -> stmt.setInt(1, hotelId),
                rs -> createRoom(rs),
                new ArrayList<>());
    }

    /**
     * Finds all hotel rooms with a specific hotel ID and guest capacity.
     *
     * @param hotelId the ID of the hotel whose rooms to find
     * @param guestCapacity the guest capacity of the rooms to find
     * @return a {@link List} of {@link HotelRoom} that match the hotel ID and guest capacity
     */
    @Override
    public List<HotelRoom> findAllByHotelIdAndGuestCapacity(
            int hotelId, int guestCapacity) {
        String sql = "SELECT hotel_room_id, guest_capacity, hotel_id" +
                    " FROM hotel_rooms" +
                    " WHERE hotel_id = ? AND guest_capacity = ?;";
        return dbUtils.executeQueryForCollection(sql,
                stmt -> {
                    stmt.setInt(1, hotelId);
                    stmt.setInt(2, guestCapacity);
                },
                rs -> createRoom(rs),
                new ArrayList<>());
    }

    /**
     * Adds a new hotel room to the database.
     *
     * @param room the {@link HotelRoom} to be added
     * @return an {@link Optional} containing the ID of the newly added hotel room
     */
    @Override
    public Optional<Integer> add(HotelRoom room) {
        String sql = "INSERT INTO hotel_rooms (guest_capacity, hotel_id)" +
                    " VALUES (?, ?);";
        return dbUtils.executeInsert(sql,
                stmt -> {
                    stmt.setInt(1, room.getGuestCapacity().getValue());
                    stmt.setInt(2, room.getHotelId());
                },
                rs -> rs.getInt("hotel_room_id"));
    }

    private HotelRoom createRoom(ResultSet rs) throws SQLException {
        return new HotelRoom(
                rs.getInt("hotel_room_id"),
                new RoomGuestCapacity(rs.getInt("guest_capacity")),
                rs.getInt("hotel_id")
        );
    }

}

