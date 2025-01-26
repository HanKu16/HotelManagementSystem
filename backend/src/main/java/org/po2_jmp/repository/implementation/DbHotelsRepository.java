package org.po2_jmp.repository.implementation;

import org.po2_jmp.domain.*;
import org.po2_jmp.entity.Address;
import org.po2_jmp.entity.Hotel;
import org.po2_jmp.repository.contract.HotelsRepository;
import org.po2_jmp.repository.helper.DbUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link HotelsRepository} interface using a relational database.
 * This class provides CRUD operations for hotels, including retrieving hotels by ID,
 * retrieving all hotels and adding new hotels to the database.
 * <p>
 * The methods in this class utilize the {@link DbUtils} utility class for executing SQL queries
 * and managing database interactions. The {@link Hotel} and {@link Address} objects are created
 * from the result sets returned by SQL queries.
 * </p>
 */
public class DbHotelsRepository implements HotelsRepository {

    private final DbUtils dbUtils;

    /**
     * Constructs a {@link DbHotelsRepository} with the specified {@link DbUtils} utility.
     *
     * @param dbUtils the {@link DbUtils} utility for executing SQL queries
     * @throws IllegalArgumentException if {@code dbUtils} is {@code null}
     */
    public DbHotelsRepository(DbUtils dbUtils) {
        if (dbUtils == null) {
            throw new IllegalArgumentException("DbUtils can not be null but null" +
                    "was passed to DbHotelsRepository constructor");
        }
        this.dbUtils = dbUtils;
    }

    /**
     * Finds a hotel by its unique ID, including its address.
     *
     * @param hotelId the ID of the hotel to find
     * @return an {@link Optional} containing the {@link Hotel} if found, or
     * {@link Optional#empty()} if not
     */
    @Override
    public Optional<Hotel> findById(int hotelId) {
        String sql = "SELECT * FROM hotels h" +
                    " LEFT JOIN hotel_addresses ha" +
                    " ON ha.hotel_address_id = h.hotel_address_id" +
                    " WHERE h.hotel_id = ?;";
        return dbUtils.executeQuery(sql,
                stmt -> stmt.setInt(1, hotelId),
                this::createHotel);
    }

    /**
     * Finds all hotels from the database, including their addresses.
     *
     * @return a list of all {@link Hotel} objects found in the database
     */
    @Override
    public List<Hotel> findAll() {
        String sql = "SELECT * FROM hotels h" +
                    " LEFT JOIN hotel_addresses ha" +
                    " ON ha.hotel_address_id = h.hotel_address_id;";
        return dbUtils.executeQueryForCollection(sql,
                stmt -> {},
                this::createHotel,
                new ArrayList<>());
    }

    /**
     * Adds a new hotel to the database.
     *
     * @param hotel the {@link Hotel} to be added
     * @return an {@link Optional} containing the ID of the newly added hotel
     */
    @Override
    public Optional<Integer> add(Hotel hotel) {
        String sql = "INSERT INTO hotels (name, description, hotel_address_id)" +
                    " VALUES (?, ?, ?);";
        return dbUtils.executeInsert(sql,
                stmt -> {
                    stmt.setString(1, hotel.getName().getValue());
                    stmt.setString(2, hotel.getDescription().getValue());
                    stmt.setInt(3, hotel.getAddress().getId());
                },
                rs -> rs.getInt("hotel_id"));
    }

    private Hotel createHotel(ResultSet rs) throws SQLException {
        Address address = new Address(
                rs.getInt("hotel_address_id"),
                new CityName(rs.getString("city")),
                new StreetName(rs.getString("street")),
                new PostalCode(rs.getString("postal_code")),
                new BuildingNumber(rs.getString("building_number"))
        );
        return new Hotel(
                rs.getInt("hotel_id"),
                new HotelName(rs.getString("name")),
                new HotelDescription(rs.getString("description")),
                address
        );
    }

}

