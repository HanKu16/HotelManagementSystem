package org.po2_jmp.repository.implementation;

import org.po2_jmp.domain.HotelAmenityName;
import org.po2_jmp.entity.HotelAmenity;
import org.po2_jmp.repository.contract.HotelAmenitiesRepository;
import org.po2_jmp.repository.helper.DbUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link HotelAmenitiesRepository} interface.
 * This class provides CRUD operations for hotel amenities using SQL queries.
 * <p>
 * The methods use the {@link DbUtils} utility class to execute SQL queries and handle
 * database interaction. The implementation assumes that a database connection is
 * managed through the {@link DbUtils} and SQL queries are executed accordingly.
 * </p>
 */
public class DbHotelAmenitiesRepository implements HotelAmenitiesRepository {

    private final DbUtils dbUtils;

    /**
     * Constructs a {@link DbHotelAmenitiesRepository} with the specified {@link DbUtils} object.
     *
     * @param dbUtils the {@link DbUtils} utility for executing SQL queries
     * @throws IllegalArgumentException if {@code dbUtils} is {@code null}
     */
    public DbHotelAmenitiesRepository(DbUtils dbUtils) {
        if (dbUtils == null) {
            throw new IllegalArgumentException("DbUtils can not be null but " +
                    "null was passed to DbHotelAmenitiesRepository constructor");
        }
        this.dbUtils = dbUtils;
    }

    /**
     * Finds a hotel amenity by its unique ID.
     *
     * @param id the ID of the hotel amenity to find
     * @return an {@link Optional} containing the {@link HotelAmenity}
     *        if found, or {@link Optional#empty()} if not
     */
    @Override
    public Optional<HotelAmenity> findById(int id) {
        String sql = "SELECT hotel_amenity_id, name, hotel_id FROM hotel_amenities" +
                    " WHERE hotel_amenity_id = ?;";
        return dbUtils.executeQuery(sql,
                stmt -> stmt.setInt(1, id),
                this::createHotelAmenity);
    }

    /**
     * Finds all hotel amenities associated with a specific hotel ID.
     *
     * @param hotelId the ID of the hotel whose amenities to find
     * @return a {@link List} of {@link HotelAmenity} associated with the hotel
     */
    @Override
    public List<HotelAmenity> findAllByHotelId(int hotelId) {
        String sql = "SELECT hotel_amenity_id, name, hotel_id FROM hotel_amenities" +
                    " WHERE hotel_id = ?;";
        return dbUtils.executeQueryForCollection(sql,
                stmt -> stmt.setInt(1, hotelId),
                this::createHotelAmenity,
                new ArrayList<>());
    }

    /**
     * Adds a new hotel amenity to the database.
     *
     * @param amenity the {@link HotelAmenity} to be added
     * @return an {@link Optional} containing the ID of the newly added hotel amenity
     */
    @Override
    public Optional<Integer> add(HotelAmenity amenity) {
        String sql = "INSERT INTO hotel_amenities (name, hotel_id)" +
                " VALUES (?, ?);";
        return dbUtils.executeInsert(sql,
                stmt -> {
                    stmt.setString(1, amenity.getName().getValue());
                    stmt.setInt(2, amenity.getHotelId());
                },
                rs -> rs.getInt("hotel_amenity_id"));
    }

    private HotelAmenity createHotelAmenity(ResultSet rs) throws SQLException {
        return new HotelAmenity(
                rs.getInt("hotel_amenity_id"),
                new HotelAmenityName(rs.getString("name")),
                rs.getInt("hotel_id")
        );
    }

}
