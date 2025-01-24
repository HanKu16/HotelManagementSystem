package org.po2_jmp.repository.implementation;

import org.po2_jmp.domain.HotelAmenityName;
import org.po2_jmp.entity.HotelAmenity;
import org.po2_jmp.repository.contract.HotelAmenitiesRepository;
import org.po2_jmp.repository.helper.DbUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbHotelAmenitiesRepository implements HotelAmenitiesRepository {

    private final DbUtils dbUtils;

    public DbHotelAmenitiesRepository(DbUtils dbUtils) {
        if (dbUtils == null) {
            throw new IllegalArgumentException("DbUtils can not be null but " +
                    "null was passed to DbHotelAmenitiesRepository constructor");
        }
        this.dbUtils = dbUtils;
    }

    @Override
    public Optional<HotelAmenity> findById(int id) {
        String sql = "SELECT hotel_amenity_id, name, hotel_id FROM hotel_amenities" +
                    " WHERE hotel_amenity_id = ?;";
        return dbUtils.executeQuery(sql,
                stmt -> stmt.setInt(1, id),
                this::createHotelAmenity);
    }

    @Override
    public List<HotelAmenity> findAllByHotelId(int hotelId) {
        String sql = "SELECT hotel_amenity_id, name, hotel_id FROM hotel_amenities" +
                    " WHERE hotel_id = ?;";
        return dbUtils.executeQueryForCollection(sql,
                stmt -> stmt.setInt(1, hotelId),
                this::createHotelAmenity,
                new ArrayList<>());
    }

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
