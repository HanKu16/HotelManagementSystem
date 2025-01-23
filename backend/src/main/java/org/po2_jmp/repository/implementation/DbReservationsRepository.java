package org.po2_jmp.repository.implementation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import org.po2_jmp.repository.contract.ReservationsRepository;
import org.po2_jmp.domain.UserId;
import org.po2_jmp.entity.Reservation;
import java.util.List;
import java.util.Optional;

public class DbReservationsRepository implements ReservationsRepository {

    private final DbUtils dbUtils;

    public DbReservationsRepository(DbUtils dbUtils) {
        if (dbUtils == null) {
            throw new IllegalArgumentException("DbUtils can not be null " +
                    "but null was passed to DbReservationsRepository constructor");
        }
        this.dbUtils = dbUtils;
    }

    @Override
    public Optional<Reservation> findById(int id) {
        String sql = "SELECT reservation_id, reservation_date, creation_datetime," +
                    " user_id, hotel_room_id" +
                    " FROM reservations" +
                    " WHERE reservation_id = ?;";
        return dbUtils.executeQuery(sql,
                stmt -> stmt.setInt(1, id),
                rs -> createReservation(rs));
    }

    @Override
    public List<Reservation> findAllByUserId(String userId) {
        String sql = "SELECT reservation_id, reservation_date, creation_datetime," +
                    " user_id, hotel_room_id" +
                    " FROM reservations" +
                    " WHERE user_id = ?;";
        return dbUtils.executeQueryForCollection(sql,
                stmt -> stmt.setString(1, userId),
                rs -> createReservation(rs),
                new ArrayList<>());
    }

    @Override
    public Optional<Reservation> findByRoomIdAndReservationDate(
            int roomId, LocalDate reservationDate) {
        String sql = "SELECT reservation_id, reservation_date, creation_datetime," +
                    " user_id, hotel_room_id" +
                    " FROM reservations" +
                    " WHERE hotel_room_id = ? AND reservation_date = ?;";
        return dbUtils.executeQuery(sql,
                stmt -> {
                    stmt.setInt(1, roomId);
                    stmt.setDate(2, java.sql.Date.valueOf(reservationDate));
                },
                rs -> createReservation(rs));
    }

    @Override
    public Optional<Integer> add(Reservation reservation) {
        String sql = "INSERT INTO reservations" +
                    " (reservation_date, creation_datetime, user_id, hotel_room_id)" +
                    " VALUES (?, ?, ?, ?);";
        return dbUtils.executeInsert(sql,
                stmt -> {
                    stmt.setDate(1, java.sql.Date.valueOf(reservation.getReservationDate()));
                    stmt.setTimestamp(2, Timestamp.valueOf(reservation.getCreationDateTime()));
                    stmt.setString(3, reservation.getUserId().getValue());
                    stmt.setInt(4, reservation.getRoomId());
                },
                rs -> rs.getInt("reservation_id"));
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM reservations WHERE reservation_id = ?;";
        return dbUtils.executeDelete(sql,
                stmt -> stmt.setInt(1, id));
    }

    private Reservation createReservation(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getInt("reservation_id"),
                rs.getDate("reservation_date").toLocalDate(),
                rs.getTimestamp("creation_datetime").toLocalDateTime(),
                new UserId(rs.getString("user_id")),
                rs.getInt("hotel_room_id")
        );
    }

}
