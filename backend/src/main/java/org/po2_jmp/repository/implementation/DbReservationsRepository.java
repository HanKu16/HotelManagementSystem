package org.po2_jmp.repository.implementation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import org.po2_jmp.repository.contract.ReservationsRepository;
import org.po2_jmp.domain.UserLogin;
import org.po2_jmp.entity.Reservation;
import java.util.List;
import java.util.Optional;

public class DbReservationsRepository implements ReservationsRepository {

    private final String url;
    private final String user;
    private final String password;

    public DbReservationsRepository(String url, String user, String password) {
        if (areAnyNullParams(url, user, password)) {
            throw new IllegalArgumentException("Url, user and password can not be " +
                    "nulls but were passed to DbReservationsRepository constructor");
        }
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Optional<Reservation> findById(int id) {
        String sql = "SELECT reservation_id, reservation_date, creation_datetime," +
                    " user_id, hotel_room_id" +
                    " FROM reservations" +
                    " WHERE reservation_id = ?;";
        Optional<Reservation> optionalReservation = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Reservation reservation = createReservation(rs);
                    optionalReservation = Optional.of(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalReservation;
    }

    public List<Reservation> findAllByUserId(String userId) {
        String sql = "SELECT reservation_id, reservation_date, creation_datetime," +
                    " user_id, hotel_room_id" +
                    " FROM reservations" +
                    " WHERE user_id = ?;";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = createReservation(rs);
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public Optional<Reservation> findByRoomIdAndReservationDate(
            int roomId, LocalDate reservationDate) {
        String sql = "SELECT reservation_id, reservation_date, creation_datetime," +
                    " user_id, hotel_room_id" +
                    " FROM reservations" +
                    " WHERE hotel_room_id = ? AND reservation_date = ?;";
        Optional<Reservation> optionalReservation = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            setSelectByRoomIdAndReservationDateQueryParams(
                    stmt, roomId, reservationDate);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Reservation reservation = createReservation(rs);
                    optionalReservation = Optional.of(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalReservation;
    }

    public Optional<Integer> add(Reservation reservation) {
        String sql = "INSERT INTO reservations" +
                    " (reservation_date, creation_datetime, user_id, hotel_room_id)" +
                    " VALUES (?, ?, ?, ?);";
        Optional<Integer> optionalReservationId = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {
            setInsertQueryParams(stmt, reservation);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        optionalReservationId = Optional.of(
                                rs.getInt("reservation_id"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalReservationId;
    }

    private Reservation createReservation(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getInt("reservation_id"),
                rs.getDate("reservation_date").toLocalDate(),
                rs.getTimestamp("creation_datetime").toLocalDateTime(),
                new UserLogin(rs.getString("user_id")),
                rs.getInt("hotel_room_id")
        );
    }

    private void setSelectByRoomIdAndReservationDateQueryParams(
            PreparedStatement stmt, int roomId, LocalDate date)
            throws SQLException {
        stmt.setInt(1, roomId);
        stmt.setDate(2, java.sql.Date.valueOf(date));
    }

    private void setInsertQueryParams(PreparedStatement stmt,
            Reservation reservation) throws SQLException {
        stmt.setDate(1, java.sql.Date.valueOf(reservation.getReservationDate()));
        stmt.setTimestamp(2, Timestamp.valueOf(reservation.getCreationDateTime()));
        stmt.setString(3, reservation.getUserId().getValue());
        stmt.setInt(4, reservation.getRoomId());
    }

    private boolean areAnyNullParams(String url,
            String user, String password) {
        return (url == null) || (user == null) ||
                (password == null);
    }

}
