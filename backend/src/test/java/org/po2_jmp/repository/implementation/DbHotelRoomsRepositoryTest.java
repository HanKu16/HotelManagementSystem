package org.po2_jmp.repository.implementation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.po2_jmp.domain.RoomGuestCapacity;
import org.po2_jmp.entity.HotelRoom;
import org.po2_jmp.repository.helper.DbUtilsImpl;
import org.po2_jmp.utils.DbTestConfigurator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class DbHotelRoomsRepositoryTest {

    private final String url = "jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1";
    private final String user = "sa";
    private final String password = "";
    private final DbUtilsImpl dbUtils = new DbUtilsImpl(url, user, password);
    private final DbTestConfigurator hotelRoomsConfigurator =
            createConfigurator("hotel_rooms");
    private final DbTestConfigurator hotelsConfigurator =
            createConfigurator("hotels");
    private final DbTestConfigurator hotelAddressesConfigurator =
            createConfigurator("hotel_addresses");
    private DbHotelRoomsRepository repository;

    @BeforeEach
    public void setUp() throws SQLException {
        this.repository = new DbHotelRoomsRepository(dbUtils);
        hotelAddressesConfigurator.create();
        hotelsConfigurator.create();
        hotelRoomsConfigurator.create();
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        hotelRoomsConfigurator.drop();
        hotelsConfigurator.drop();
        hotelAddressesConfigurator.drop();
    }

    @Test
    void FindById_ShouldReturnPresentOptional_WhenIdIs2() throws SQLException {
        insertData();
        Optional<HotelRoom> optionalHotelRoom = repository.findById(2);
        assertTrue(optionalHotelRoom.isPresent());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenIdIs35() throws SQLException {
        insertData();
        Optional<HotelRoom> optionalHotelRoom = repository.findById(35);
        assertTrue(optionalHotelRoom.isEmpty());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenTableIsEmpty() {
        Optional<HotelRoom> optionalHotelRoom = repository.findById(5);
        assertTrue(optionalHotelRoom.isEmpty());
    }

    @Test
    void FindById_ShouldReturnCorrectRoom_WhenIdIs7() throws SQLException {
        insertData();
        Optional<HotelRoom> optionalHotelRoom= repository.findById(7);
        assertTrue(optionalHotelRoom.isPresent());
        HotelRoom hotelRoom = optionalHotelRoom.get();
        assertEquals(7, hotelRoom.getId());
        assertEquals(3, hotelRoom.getGuestCapacity().getValue());
        assertEquals(2, hotelRoom.getHotelId());
    }

    @Test
    void FindAllByHotelId_ShouldReturn0Records_WhenTableIsEmpty() {
        List<HotelRoom> hotelRooms = repository.findAllByHotelId(3);
        assertEquals(0, hotelRooms.size());
    }

    @Test
    void FindAllByHotelId_ShouldReturn5Records_WhenHotelIdIs2() throws SQLException {
        insertData();
        List<HotelRoom> hotelRooms = repository.findAllByHotelId(2);
        assertEquals(5, hotelRooms.size());
    }

    @Test
    void FindAllByHotelIdAndGuestCapacity_ShouldReturn0Records_WhenHotelIdIs2AndGuestCapacityIs4()
            throws SQLException {
        insertData();
        List<HotelRoom> hotelRooms = repository.findAllByHotelIdAndGuestCapacity(2, 4);
        assertEquals(0, hotelRooms.size());
    }

    @Test
    void FindAllByHotelIdAndGuestCapacity_ShouldReturn3Records_WhenHotelIdIs3AndGuestCapacityIs3()
            throws SQLException {
        insertData();
        List<HotelRoom> hotelRooms = repository.findAllByHotelIdAndGuestCapacity(3, 3);
        assertEquals(3, hotelRooms.size());
    }

    @Test
    void FindAllByHotelIdAndGuestCapacity_ShouldReturn0Records_WhenHotelOfGivenIdDoesNotExist()
            throws SQLException {
        insertData();
        List<HotelRoom> hotelRooms = repository.findAllByHotelIdAndGuestCapacity(17, 3);
        assertEquals(0, hotelRooms.size());
    }

    @Test
    void Add_ShouldReturnPresentOptional_WhenHotelRoomIsValid()
            throws SQLException {
        insertData();
        HotelRoom hotelRoom = new HotelRoom(new RoomGuestCapacity(3), 2);
        Optional<Integer> optionalHotelRoomId = repository.add(hotelRoom);
        assertTrue(optionalHotelRoomId.isPresent());
    }

    private void insertData() throws SQLException {
        hotelAddressesConfigurator.insert();
        hotelsConfigurator.insert();
        hotelRoomsConfigurator.insert();
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