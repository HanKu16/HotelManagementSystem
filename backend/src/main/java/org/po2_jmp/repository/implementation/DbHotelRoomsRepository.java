package org.po2_jmp.repository.implementation;

import org.po2_jmp.domain.RoomGuestCapacity;
import org.po2_jmp.entity.HotelRoom;
import org.po2_jmp.repository.contract.HotelRoomsRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbHotelRoomsRepository implements HotelRoomsRepository {

    private final DbUtils dbUtils;

    public DbHotelRoomsRepository(DbUtils dbUtils) {
        if (dbUtils == null) {
            throw new IllegalArgumentException("DbUtils can not be null but " +
                    "null was passed to DbHotelRoomsRepository constructor");
        }
        this.dbUtils = dbUtils;
    }

    @Override
    public Optional<HotelRoom> findById(int id) {
        String sql = "SELECT hotel_room_id, guest_capacity, hotel_id" +
                    " FROM hotel_rooms" +
                    " WHERE hotel_room_id = ?;";
        return dbUtils.executeQuery(sql,
                stmt -> stmt.setInt(1, id),
                rs -> createRoom(rs));
    }

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

