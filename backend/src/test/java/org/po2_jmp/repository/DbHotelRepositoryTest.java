package org.po2_jmp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.po2_jmp.entity.Hotel;
import org.po2_jmp.utils.DbTestConfigurator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class DbHotelRepositoryTest {

    private final String url = "jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1";
    private final String user = "sa";
    private final String password = "";
    private final DbTestConfigurator hotelsConfigurator =
            createConfigurator("hotels");
    private final DbTestConfigurator hotelAddressesConfigurator =
            createConfigurator("hotel_addresses");
    private final DbHotelRepository hotelRepository =
            new DbHotelRepository(url, user, password);

    @BeforeEach
    public void setUp() throws SQLException {
        hotelsConfigurator.drop();
        hotelAddressesConfigurator.drop();
        hotelAddressesConfigurator.create();
        hotelsConfigurator.create();
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenUrlIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DbHotelRepository(null, user, password);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenUserIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DbHotelRepository(url, null, password);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPasswordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DbHotelRepository(url, user, null);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenParamsAreValid() {
        DbHotelRepository repository = new DbHotelRepository(url, user, password);
        assertNotNull(repository);
    }

    @Test
    void FindById_ShouldReturnPresentOptional_WhenIdIs4() throws SQLException {
        insertData();
        Optional<Hotel> optionalAddress = hotelRepository.findById(4);
        assertTrue(optionalAddress.isPresent());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenIdIs9() throws SQLException {
        insertData();
        Optional<Hotel> optionalHotel = hotelRepository.findById(9);
        assertTrue(optionalHotel.isEmpty());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenTableIsEmpty() {
        Optional<Hotel> optionalAddress = hotelRepository.findById(3);
        assertTrue(optionalAddress.isEmpty());
    }

    @Test
    void FindById_ShouldReturnCorrectAddress_WhenIdIs3() throws SQLException {
        insertData();
        Optional<Hotel> optionalHotel = hotelRepository.findById(4);
        assertTrue(optionalHotel.isPresent());
        Hotel hotel = optionalHotel.get();
        assertEquals("Kapitol", hotel.getName().getValue());
        assertEquals("Hotel Kapitol jest najwyższym hotelem w kraju.",
                hotel.getDescription().getValue());
        assertEquals(4, hotel.getAddress().getId());
        assertEquals("Białystok", hotel.getAddress().getCity().getValue());
        assertEquals("15-109", hotel.getAddress().getPostalCode().getValue());
        assertEquals("78B", hotel.getAddress().getBuildingNumber().getValue());
    }

    @Test
    void FindAll_ShouldReturn0Records_WhenTableIsEmpty() {
        List<Hotel> hotels = hotelRepository.findAll();
        assertEquals(0, hotels.size());
    }

    @Test
    void FindAll_ShouldReturn5Records_When5RecordsInTheTable() throws SQLException {
        insertData();
        List<Hotel> hotels = hotelRepository.findAll();
        assertEquals(5, hotels.size());
    }

    private void insertData() throws SQLException {
        hotelAddressesConfigurator.insert();
        hotelsConfigurator.insert();
    }

    private DbTestConfigurator createConfigurator(String tableName) {
        try {
             return new DbTestConfigurator(
                    url, user, password, tableName);
        } catch (IOException e) {
            throw new RuntimeException("Error creating DbTestConfigurator " +
                    "for table: " + tableName, e);
        }
    }

}