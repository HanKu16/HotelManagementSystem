package org.po2_jmp.repository.implementation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.po2_jmp.domain.UserId;
import org.po2_jmp.entity.Reservation;
import org.po2_jmp.repository.helper.DbUtilsImpl;
import org.po2_jmp.utils.DbTestConfigurator;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

class DbReservationsRepositoryTest {

    private final String url = "jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1";
    private final String user = "sa";
    private final String password = "";
    private final DbUtilsImpl dbUtils = new DbUtilsImpl(url, user, password);
    private final DbTestConfigurator reservationsConfigurator =
            createConfigurator("reservations");
    private final DbTestConfigurator usersConfigurator = createConfigurator("users");
    private final DbTestConfigurator rolesConfigurator = createConfigurator("roles");
    private final DbTestConfigurator hotelRoomsConfigurator =
            createConfigurator("hotel_rooms");
    private final DbTestConfigurator hotelsConfigurator = createConfigurator("hotels");
    private final DbTestConfigurator hotelAddressesConfigurator =
            createConfigurator("hotel_addresses");
    private DbReservationsRepository repository;

    @BeforeEach
    public void setUp() throws SQLException {
        repository = new DbReservationsRepository(dbUtils);
        hotelAddressesConfigurator.create();
        hotelsConfigurator.create();
        hotelRoomsConfigurator.create();
        rolesConfigurator.create();
        usersConfigurator.create();
        reservationsConfigurator.create();
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        reservationsConfigurator.drop();
        usersConfigurator.drop();
        rolesConfigurator.drop();
        hotelRoomsConfigurator.drop();
        hotelsConfigurator.drop();
        hotelAddressesConfigurator.drop();
    }

    @Test
    void FindById_ShouldReturnPresentOptional_WhenIdIsIs5() throws SQLException {
        insertData();
        Optional<Reservation> optionalReservation = repository.findById(5);
        assertTrue(optionalReservation.isPresent());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenIdIs100() throws SQLException {
        insertData();
        Optional<Reservation> optionalReservation = repository.findById(100);
        assertTrue(optionalReservation.isEmpty());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenTableIsEmpty() {
        Optional<Reservation> optionalReservation = repository.findById(2);
        assertTrue(optionalReservation.isEmpty());
    }

    @Test
    void FindAllByUserId_ShouldReturn14Reservations_WhenUserIdIsSzpakuAndTableIsNotEmpty()
            throws SQLException {
        insertData();
        List<Reservation> reservations = repository.findAllByUserId("szpaku");
        assertEquals(13, reservations.size());
    }

    @Test
    void FindAllByUserId_ShouldReturn0Reservations_WhenUserIdIsSzpakuAndTableIsEmpty() {
        List<Reservation> reservations = repository.findAllByUserId("szpaku");
        assertEquals(0, reservations.size());
    }

    @Test
    void FindAllByUserId_ShouldReturn0Reservation_WhenUserIdIsPablo()
            throws SQLException {
        insertData();
        List<Reservation> reservations = repository.findAllByUserId("pablo");
        assertEquals(0, reservations.size());
    }

    @Test
    void FindByRoomIdAndReservationDate_ShouldReturnPresentOptional_WhenRoomIdIs4AndReservationDateIs20241221()
            throws SQLException {
        insertData();
        Optional<Reservation> optionalReservation = repository.findByRoomIdAndReservationDate(
                4, LocalDate.of(2024, 12, 21));
        assertTrue(optionalReservation.isPresent());
    }

    @Test
    void FindByRoomIdAndReservationDate_ShouldReturnEmptyOptional_WhenRoomIdIs4AndReservationDateIs20241231()
            throws SQLException {
        insertData();
        Optional<Reservation> optionalReservation = repository.findByRoomIdAndReservationDate(
                4, LocalDate.of(2024, 12, 31));
        assertTrue(optionalReservation.isEmpty());
    }

    @Test
    void FindByRoomIdAndReservationDate_ShouldReturnEmptyOptional_WhenTableIsEmpty() {
        Optional<Reservation> optionalReservation = repository.findByRoomIdAndReservationDate(
                4, LocalDate.of(2024, 12, 21));
        assertTrue(optionalReservation.isEmpty());
    }

    @Test
    void Add_ShouldReturnPresentOptional_WhenReservationIsValid()
            throws SQLException {
        insertData();
        Reservation reservation = new Reservation(
                LocalDate.of(2024, 12, 29),
                LocalDateTime.of(2024, 12, 28, 6, 30, 0, 0),
                new UserId("szpaku"),
                5);
        Optional<Integer> optionalReservationId = repository.add(reservation);
        assertTrue(optionalReservationId.isPresent());
    }

    @ParameterizedTest
    @ValueSource(ints = {-3, 102, 1001})
    void DeleteById_ShouldReturn0_WhenReservationOfGivenIdDoesNotExist(
            int reservationId) throws SQLException {
        insertData();
        int removedRows = repository.deleteById(reservationId);
        assertEquals(0, removedRows);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 7, 15})
    void DeleteById_ShouldReturn1_WhenReservationOfGivenIdDoesNotExist(
            int reservationId) throws SQLException {
        insertData();
        int removedRows = repository.deleteById(reservationId);
        assertEquals(1, removedRows);
    }

    private void insertData() throws SQLException {
        hotelAddressesConfigurator.insert();
        hotelsConfigurator.insert();
        hotelRoomsConfigurator.insert();
        rolesConfigurator.insert();
        usersConfigurator.insert();
        reservationsConfigurator.insert();
    }

    private DbTestConfigurator createConfigurator(String tableName) {
        try {
            return new DbTestConfigurator(url, user, password, tableName);
        } catch (IOException e) {
            throw new RuntimeException("Error creating DbTestConfigurator " +
                    "for table: " + tableName, e);
        }
    }

}