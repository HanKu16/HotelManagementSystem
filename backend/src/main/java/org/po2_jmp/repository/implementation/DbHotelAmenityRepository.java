package org.po2_jmp.repository.implementation;

import org.po2_jmp.domain.HotelAmenityName;
import org.po2_jmp.entity.HotelAmenity;
import org.po2_jmp.repository.contract.HotelAmenityRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbHotelAmenityRepository implements HotelAmenityRepository {

    private final String url;
    private final String user;
    private final String password;

    public DbHotelAmenityRepository(String url,
            String user, String password) {
        if (areAnyNullParams(url, user, password)) {
            throw new IllegalArgumentException("Url, user and password can not be " +
                    "nulls but were passed to DbHotelAmenityRepository constructor");
        }
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Optional<HotelAmenity> findById(int id) {
        String sql = "SELECT hotel_amenity_id, name, hotel_id FROM hotel_amenities" +
                    " WHERE hotel_amenity_id = ?;";
        Optional<HotelAmenity> optionalHotelAmenity = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    HotelAmenity hotelAmenity = createHotelAmenity(rs);
                    optionalHotelAmenity = Optional.of(hotelAmenity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalHotelAmenity;
    }

    @Override
    public List<HotelAmenity> findAllByHotelId(int hotelId) {
        String sql = "SELECT hotel_amenity_id, name, hotel_id FROM hotel_amenities" +
                    " WHERE hotel_id = ?;";
        List<HotelAmenity> amenities = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, hotelId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    HotelAmenity amenity = createHotelAmenity(rs);
                    amenities.add(amenity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amenities;
    }

    @Override
    public Optional<Integer> add(HotelAmenity amenity) {
        String sql = "INSERT INTO hotel_amenities (name, hotel_id)" +
                " VALUES (?, ?);";
        Optional<Integer> optionAmenityId = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {
            setInsertQueryParams(stmt, amenity);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        optionAmenityId = Optional.of(
                                rs.getInt("hotel_amenity_id"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionAmenityId;
    }

    private HotelAmenity createHotelAmenity(ResultSet rs) throws SQLException {
        return new HotelAmenity(
                rs.getInt("hotel_amenity_id"),
                new HotelAmenityName(rs.getString("name")),
                rs.getInt("hotel_id")
        );
    }

    private void setInsertQueryParams(PreparedStatement stmt,
            HotelAmenity amenity) throws SQLException {
        stmt.setString(1, amenity.getName().getValue());
        stmt.setInt(2, amenity.getHotelId());
    }

    private boolean areAnyNullParams(String url,
                                     String user, String password) {
        return (url == null) || (user == null) || (password == null);
    }

}
