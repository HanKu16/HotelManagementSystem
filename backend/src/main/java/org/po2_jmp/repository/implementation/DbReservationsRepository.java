package org.po2_jmp.repository.implementation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import org.po2_jmp.repository.contract.ReservationsRepository;
import org.po2_jmp.domain.UserId;
import org.po2_jmp.entity.Reservation;
import org.po2_jmp.repository.helper.DbUtils;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link ReservationsRepository} interface using a relational database.
 * This class provides CRUD operations for reservations, including retrieving reservations by ID,
 * retrieving reservations by user ID or room ID with reservation date, and adding or deleting reservations
 * from the database.
 * <p>
 * The methods in this class utilize the {@link DbUtils} utility class for executing SQL
 * queries and managing database interactions. The {@link Reservation} objects are created
 * from the result sets returned by SQL queries.
 * </p>

 */
public class DbReservationsRepository implements ReservationsRepository {

    private final DbUtils dbUtils;

    /**
     * Constructs a {@link DbReservationsRepository} with the specified {@link DbUtils} utility.
     *
     * @param dbUtils the {@link DbUtils} utility for executing SQL queries
     * @throws IllegalArgumentException if {@code dbUtils} is {@code null}
     */
    public DbReservationsRepository(DbUtils dbUtils) {
        if (dbUtils == null) {
            throw new IllegalArgumentException("DbUtils can not be null " +
                    "but null was passed to DbReservationsRepository constructor");
        }
        this.dbUtils = dbUtils;
    }

    /**
     * Finds a reservation by its unique ID.
     *
     * @param id the ID of the reservation to find
     * @return an {@link Optional} containing the {@link Reservation} if found,
     * or {@link Optional#empty()} if not
     */
    @Override
    public Optional<Reservation> findById(int id) {
        String sql = "SELECT reservation_id, reservation_date, creation_datetime," +
                    " user_id, hotel_room_id" +
                    " FROM reservations" +
                    " WHERE reservation_id = ?;";
        return dbUtils.executeQuery(sql,
                stmt -> stmt.setInt(1, id),
                this::createReservation);
    }

    /**
     * Finds all reservations for a specific user, identified by the user ID.
     *
     * @param userId the user ID to search for reservations
     * @return a list of {@link Reservation} objects associated with the given user ID
     */
    @Override
    public List<Reservation> findAllByUserId(String userId) {
        String sql = "SELECT reservation_id, reservation_date, creation_datetime," +
                    " user_id, hotel_room_id" +
                    " FROM reservations" +
                    " WHERE user_id = ?;";
        return dbUtils.executeQueryForCollection(sql,
                stmt -> stmt.setString(1, userId),
                this::createReservation,
                new ArrayList<>());
    }

    /**
     * Finds a reservation based on room ID and reservation date.
     *
     * @param roomId the ID of the hotel room
     * @param reservationDate the date the reservation was made
     * @return an {@link Optional} containing the {@link Reservation} if found, or
     * {@link Optional#empty()} if not
     */
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
                this::createReservation);
    }

    /**
     * Adds a new reservation to the database.
     *
     * @param reservation the {@link Reservation} to be added
     * @return an {@link Optional} containing the ID of the newly added reservation
     */
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

    /**
     * Deletes a reservation from the database by its unique ID.
     *
     * @param id the ID of the reservation to delete
     * @return the number of rows affected
     */
    @Override
    public int deleteById(int id) {
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
