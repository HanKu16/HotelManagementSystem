package org.po2_jmp.repository.implementation;

import org.po2_jmp.domain.BuildingNumber;
import org.po2_jmp.domain.CityName;
import org.po2_jmp.domain.PostalCode;
import org.po2_jmp.entity.Address;
import org.po2_jmp.repository.contract.HotelAddressRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbHotelAddressRepository implements HotelAddressRepository {

    private final String url;
    private final String user;
    private final String password;

    public DbHotelAddressRepository(String url, String user, String password) {
        if (areAnyNullParams(url, user, password)) {
            throw new IllegalArgumentException("Url, user and password can not be " +
                    "nulls but were passed to DbHotelAddressRepository constructor");
        }
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Optional<Address> findById(int id)  {
        String sql = "SELECT hotel_address_id, city, postal_code, building_number" +
                    " FROM hotel_addresses" +
                    " WHERE hotel_address_id = ?";
        Optional<Address> optionalAddress = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Address address = createAddress(rs);
                    optionalAddress = Optional.of(address);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalAddress;
    }

    public List<Address> findAll() {
        String sql = "SELECT hotel_address_id, city, postal_code, building_number" +
                    " FROM hotel_addresses";
        List<Address> addresses = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Address address = createAddress(rs);
                addresses.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    public Optional<Integer> add(Address address) {
        String sql = "INSERT INTO hotel_addresses (city, postal_code, building_number)" +
                    " VALUES (?, ?, ?);";
        Optional<Integer> optionalAddressId = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {
            setInsertQueryParams(stmt, address);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        optionalAddressId = Optional.of(
                                rs.getInt("hotel_address_id"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalAddressId;
    }

    private Address createAddress(ResultSet rs) throws SQLException {
        return new Address(
                rs.getInt("hotel_address_id"),
                new CityName(rs.getString("city")),
                new PostalCode(rs.getString("postal_code")),
                new BuildingNumber(rs.getString("building_number"))
        );
    }

    private void setInsertQueryParams(PreparedStatement stmt,
            Address address) throws SQLException {
        stmt.setString(1, address.getCity().getValue());
        stmt.setString(2, address.getPostalCode().getValue());
        stmt.setString(3, address.getBuildingNumber().getValue());
    }

    private boolean areAnyNullParams(String url,
            String user, String password) {
        return (url == null) || (user == null) || (password == null);
    }

}
