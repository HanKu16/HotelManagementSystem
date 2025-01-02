package org.po2_jmp.repository.implementation;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.po2_jmp.domain.BuildingNumber;
import org.po2_jmp.domain.CityName;
import org.po2_jmp.domain.PostalCode;
import org.po2_jmp.domain.StreetName;
import org.po2_jmp.entity.Address;
import org.po2_jmp.utils.DbTestConfigurator;

class DbHotelAddressesRepositoryTest {

    private final String url = "jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1";
    private final String user = "sa";
    private final String password = "";
    private final DbTestConfigurator configurator = createConfigurator();
    private DbHotelAddressesRepository repository;

    @BeforeEach
    public void setUp() throws SQLException {
        this.repository = new DbHotelAddressesRepository(url, user, password);
        configurator.create();
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        configurator.drop();
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenUrlIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
           new DbHotelAddressesRepository(null, user, password);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenUserIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DbHotelAddressesRepository(url, null, password);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPasswordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DbHotelAddressesRepository(url, user, null);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenAllParamsAreNotNulls() {
        DbHotelAddressesRepository repository = new DbHotelAddressesRepository(
                url, user, password);
        assertNotNull(repository);
    }

    @Test
    void FindById_ShouldReturnPresentOptional_WhenIdIs2() throws SQLException {
        configurator.insert();
        Optional<Address> optionalAddress = repository.findById(2);
        assertTrue(optionalAddress.isPresent());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenIdIs6() throws SQLException {
        configurator.insert();
        Optional<Address> optionalAddress = repository.findById(6);
        assertTrue(optionalAddress.isEmpty());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenTableIsEmpty() {
        Optional<Address> optionalAddress = repository.findById(1);
        assertTrue(optionalAddress.isEmpty());
    }

    @Test
    void FindById_ShouldReturnCorrectAddress_WhenIdIs3() throws SQLException {
        configurator.insert();
        Optional<Address> optionalAddress = repository.findById(3);
        assertTrue(optionalAddress.isPresent());
        Address address = optionalAddress.get();
        assertEquals("Gdańsk", address.getCity().getValue());
        assertEquals("80-011", address.getPostalCode().getValue());
        assertEquals("2", address.getBuildingNumber().getValue());
    }

    @Test
    void FindAll_ShouldReturn0Records_WhenTableIsEmpty() {
        List<Address> addresses = repository.findAll();
        assertEquals(0, addresses.size());
    }

    @Test
    void FindAll_ShouldReturn5Records_When5RecordsInTheTable() throws SQLException {
        configurator.insert();
        List<Address> addresses = repository.findAll();
        assertEquals(5, addresses.size());
    }

    @Test
    void FindAll_ShouldReturnCorrectRecords_When5RecordsInTheTable() throws SQLException {
        List<String> cities = List.of("Warszawa", "Kraków", "Gdańsk",
                "Białystok", "Wrocław");
        List<String> postalCodes = List.of("00-021", "30-134", "80-011",
                "15-109", "50-007");
        List<String> buildingNumbers = List.of("15", "21A", "2", "78B", "6");

        configurator.insert();
        for (int i = 1; i < 6; ++i) {
            Optional<Address> optionalAddress = repository.findById(i);
            assertTrue(optionalAddress.isPresent());
            Address address = optionalAddress.get();
            int cityDataIndex = i - 1;
            assertEquals(cities.get(cityDataIndex),
                    address.getCity().getValue());
            assertEquals(postalCodes.get(cityDataIndex),
                    address.getPostalCode().getValue());
            assertEquals(buildingNumbers.get(cityDataIndex),
                    address.getBuildingNumber().getValue());
        }
    }

    @Test
    void Add_ShouldReturnPresentOptional_WhenAddressIsValidAndTableIsNotEmpty()
            throws SQLException {
        configurator.insert();
        Address address = new Address(
                new CityName("Kielce"),
                new StreetName("Ogrodowa"),
                new PostalCode("25-751"),
                new BuildingNumber("13"));
        Optional<Integer> optionalAddressId = repository.add(address);
        assertTrue(optionalAddressId.isPresent());
    }

    private DbTestConfigurator createConfigurator() {
        String tableName = "hotel_addresses";

        try {
            return new DbTestConfigurator(url, user, password, tableName);
        } catch (IOException e) {
            throw new RuntimeException("Error creating DbTestConfigurator " +
                    "for table: " + tableName, e);
        }
    }

}