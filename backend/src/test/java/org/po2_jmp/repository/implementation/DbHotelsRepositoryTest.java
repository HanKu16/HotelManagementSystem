package org.po2_jmp.repository.implementation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.po2_jmp.entity.Hotel;
import org.po2_jmp.repository.helper.DbUtilsImpl;
import org.po2_jmp.utils.DbTestConfigurator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class DbHotelsRepositoryTest {

    private final String url = "jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1";
    private final String user = "sa";
    private final String password = "";
    private final DbUtilsImpl dbUtils = new DbUtilsImpl(url, user, password);
    private final DbTestConfigurator hotelsConfigurator =
            createConfigurator("hotels");
    private final DbTestConfigurator hotelAddressesConfigurator =
            createConfigurator("hotel_addresses");
    private final DbHotelsRepository repository =
            new DbHotelsRepository(dbUtils);

    @BeforeEach
    public void setUp() throws SQLException {
        hotelAddressesConfigurator.create();
        hotelsConfigurator.create();
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        hotelsConfigurator.drop();
        hotelAddressesConfigurator.drop();
    }

    @Test
    void FindById_ShouldReturnPresentOptional_WhenIdIs4() throws SQLException {
        insertData();
        Optional<Hotel> optionalAddress = repository.findById(4);
        assertTrue(optionalAddress.isPresent());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenIdIs9() throws SQLException {
        insertData();
        Optional<Hotel> optionalHotel = repository.findById(9);
        assertTrue(optionalHotel.isEmpty());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenTableIsEmpty() {
        Optional<Hotel> optionalAddress = repository.findById(3);
        assertTrue(optionalAddress.isEmpty());
    }

    @Test
    void FindById_ShouldReturnCorrectAddress_WhenIdIs3() throws SQLException {
        insertData();
        Optional<Hotel> optionalHotel = repository.findById(4);
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
        List<Hotel> hotels = repository.findAll();
        assertEquals(0, hotels.size());
    }

    @Test
    void FindAll_ShouldReturn5Records_When5RecordsInTheTable() throws SQLException {
        insertData();
        List<Hotel> hotels = repository.findAll();
        assertEquals(5, hotels.size());
    }

    private void insertData() throws SQLException {
        hotelAddressesConfigurator.insert();
        hotelsConfigurator.insert();
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