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

public class DbHotelsRepository implements HotelsRepository {

    private final DbUtils dbUtils;

    public DbHotelsRepository(DbUtils dbUtils) {
        if (dbUtils == null) {
            throw new IllegalArgumentException("DbUtils can not be null but null" +
                    "was passed to DbHotelsRepository constructor");
        }
        this.dbUtils = dbUtils;
    }

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

