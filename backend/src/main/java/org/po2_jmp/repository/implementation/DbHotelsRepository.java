package org.po2_jmp.repository.implementation;

import org.po2_jmp.domain.*;
import org.po2_jmp.entity.Address;
import org.po2_jmp.entity.Hotel;
import org.po2_jmp.repository.contract.HotelsRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbHotelsRepository implements HotelsRepository {

    private final String url;
    private final String user;
    private final String password;

    public DbHotelsRepository(String url, String user, String password) {
        if (areAnyNullParams(url, user, password)) {
            throw new IllegalArgumentException("Url, user and password can not be " +
                    "nulls but were passed to DbHotelsRepository constructor");
        }
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Optional<Hotel> findById(int hotelId) {
        String sql = "SELECT * FROM hotels h" +
                    " LEFT JOIN hotel_addresses ha" +
                    " ON ha.hotel_address_id = h.hotel_address_id" +
                    " WHERE h.hotel_id = ?;";
        Optional<Hotel> optionalHotel = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, hotelId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Hotel hotel = createHotel(rs);
                    optionalHotel = Optional.of(hotel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalHotel;
    }

    @Override
    public List<Hotel> findAll() {
        String sql = "SELECT * FROM hotels h" +
                    " LEFT JOIN hotel_addresses ha" +
                    " ON ha.hotel_address_id = h.hotel_address_id;";
        List<Hotel> hotels = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Hotel hotel = createHotel(rs);
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public Optional<Integer> add(Hotel hotel) {
        String sql = "INSERT INTO hotels (name, description, hotel_address_id)" +
                    " VALUES (?, ?, ?);";
        Optional<Integer> optionalHotelId = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {
            setInsertQueryParams(stmt, hotel);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        optionalHotelId = Optional.of(rs.getInt("hotel_id"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalHotelId;
    }

    private Hotel createHotel(ResultSet rs) throws SQLException {
        Address address = new Address(
                rs.getInt("hotel_address_id"),
                new CityName(rs.getString("city")),
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

    private void setInsertQueryParams(PreparedStatement stmt,
            Hotel hotel) throws SQLException {
        stmt.setString(1, hotel.getName().getValue());
        stmt.setString(2, hotel.getDescription().getValue());
        stmt.setInt(3, hotel.getAddress().getId());
    }

    private boolean areAnyNullParams(String url,
            String user, String password) {
        return (url == null) || (user == null) || (password == null);
    }

}

