package org.po2_jmp.repository.implementation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.po2_jmp.domain.HotelAmenityName;
import org.po2_jmp.entity.HotelAmenity;
import org.po2_jmp.utils.DbTestConfigurator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class DbHotelAmenitiesRepositoryTest {

    private final String url = "jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1";
    private final String user = "sa";
    private final String password = "";
    private final DbTestConfigurator hotelsConfigurator =
            createConfigurator("hotels");
    private final DbTestConfigurator hotelAddressesConfigurator =
            createConfigurator("hotel_addresses");
    private final DbTestConfigurator hotelAmenitiesConfigurator =
            createConfigurator("hotel_amenities");
    private DbHotelAmenitiesRepository repository;

    @BeforeEach
    public void setUp() throws SQLException {
        this.repository = new DbHotelAmenitiesRepository(
                url, user, password);
        hotelAddressesConfigurator.create();
        hotelsConfigurator.create();
        hotelAmenitiesConfigurator.create();
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        hotelAmenitiesConfigurator.drop();
        hotelsConfigurator.drop();
        hotelAddressesConfigurator.drop();
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenUrlIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DbHotelAmenitiesRepository(null, user, password);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenUserIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DbHotelAmenitiesRepository(url, null, password);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPasswordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DbHotelAmenitiesRepository(url, user, null);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenAllParamsAreNotNulls() {
        DbHotelAmenitiesRepository repository = new DbHotelAmenitiesRepository(
                url, user, password);
        assertNotNull(repository);
    }

    @Test
    void FindById_ShouldReturnPresentOptional_WhenIdIs5() throws SQLException {
        insertData();
        Optional<HotelAmenity> optionalHotelAmenity = repository.findById(5);
        assertTrue(optionalHotelAmenity.isPresent());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenIdIs71() throws SQLException {
        insertData();
        Optional<HotelAmenity> optionalHotelAmenity = repository.findById(71);
        assertTrue(optionalHotelAmenity.isEmpty());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenTableIsEmpty() {
        Optional<HotelAmenity> optionalHotelAmenity = repository.findById(5);
        assertTrue(optionalHotelAmenity.isEmpty());
    }

    @Test
    void FindById_ShouldReturnCorrectRoom_WhenIdIs4() throws SQLException {
        insertData();
        Optional<HotelAmenity> optionalHotelAmenity = repository.findById(4);
        assertTrue(optionalHotelAmenity.isPresent());
        HotelAmenity hotelAmenity = optionalHotelAmenity.get();
        assertEquals(4, hotelAmenity.getId());
        assertEquals(new HotelAmenityName("Siłownia"), hotelAmenity.getName());
        assertEquals(2, hotelAmenity.getHotelId());
    }

    @Test
    void FindAllByHotelId_ShouldReturn0Records_WhenTableIsEmpty() {
        List<HotelAmenity> hotelAmenities = repository.findAllByHotelId(3);
        assertEquals(0, hotelAmenities.size());
    }

    @Test
    void FindAllByHotelId_ShouldReturn3Records_WhenHotelIdIs4() throws SQLException {
        insertData();
        List<HotelAmenity> hotelAmenities = repository.findAllByHotelId(4);
        assertEquals(3, hotelAmenities.size());
    }

    @Test
    void Add_ShouldReturnPresentOptional_WhenHotelRoomIsValid() throws SQLException {
        insertData();
        HotelAmenity hotelAmenity = new HotelAmenity(
                new HotelAmenityName("Plac zabaw"), 2);
        Optional<Integer> optionalHotelAmenityId =
                repository.add(hotelAmenity);
        assertTrue(optionalHotelAmenityId.isPresent());
    }

    private void insertData() throws SQLException {
        hotelAddressesConfigurator.insert();
        hotelsConfigurator.insert();
        hotelAmenitiesConfigurator.insert();
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