package org.po2_jmp.repository.implementation;

import org.po2_jmp.domain.RoomGuestCapacity;
import org.po2_jmp.entity.HotelRoom;
import org.po2_jmp.repository.contract.HotelRoomsRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbHotelRoomsRepository implements HotelRoomsRepository {

    private final String url;
    private final String user;
    private final String password;

    public DbHotelRoomsRepository(String url, String user, String password) {
        if (areAnyNullParams(url, user, password)) {
            throw new IllegalArgumentException("Url, user and password can not be " +
                    "nulls but were passed to DbHotelRoomsRepository constructor");
        }
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Optional<HotelRoom> findById(int id) {
        String sql = "SELECT hotel_room_id, guest_capacity, hotel_id" +
                    " FROM hotel_rooms" +
                    " WHERE hotel_room_id = ?;";
        Optional<HotelRoom> optionalRoom = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    HotelRoom room = createRoom(rs);
                    optionalRoom = Optional.of(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalRoom;
    }

    @Override
    public List<HotelRoom> findAllByHotelId(int hotelId) {
        String sql = "SELECT hotel_room_id, guest_capacity, hotel_id" +
                    " FROM hotel_rooms" +
                    " WHERE hotel_id = ?;";
        List<HotelRoom> rooms = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, hotelId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    HotelRoom room = createRoom(rs);
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public List<HotelRoom> findAllByHotelIdAndGuestCapacity(
            int hotelId, int guestCapacity) {
        String sql = "SELECT hotel_room_id, guest_capacity, hotel_id" +
                    " FROM hotel_rooms" +
                    " WHERE hotel_id = ? AND guest_capacity = ?;";
        List<HotelRoom> rooms = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            setSelectByHotelIdAndGuestCapacityParams(stmt, hotelId, guestCapacity);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    HotelRoom room = createRoom(rs);
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public Optional<Integer> add(HotelRoom room) {
        String sql = "INSERT INTO hotel_rooms (guest_capacity, hotel_id)" +
                    " VALUES (?, ?);";
        Optional<Integer> optionalRoomId = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {
            setInsertQueryParams(stmt, room);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        optionalRoomId = Optional.of(
                                rs.getInt("hotel_room_id"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalRoomId;
    }

    private HotelRoom createRoom(ResultSet rs) throws SQLException {
        return new HotelRoom(
                rs.getInt("hotel_room_id"),
                new RoomGuestCapacity(rs.getInt("guest_capacity")),
                rs.getInt("hotel_id")
        );
    }

    private void setInsertQueryParams(PreparedStatement stmt,
            HotelRoom room) throws SQLException {
        stmt.setInt(1, room.getGuestCapacity().getValue());
        stmt.setInt(2, room.getHotelId());
    }

    private void setSelectByHotelIdAndGuestCapacityParams(PreparedStatement stmt,
            int hotelId, int guestCapacity) throws SQLException {
        stmt.setInt(1, hotelId);
        stmt.setInt(2, guestCapacity);
    }

    private boolean areAnyNullParams(String url,
            String user, String password) {
        return (url == null) || (user == null) || (password == null);
    }

}

