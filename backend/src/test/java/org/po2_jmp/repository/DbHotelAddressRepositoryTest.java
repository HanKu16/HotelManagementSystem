package org.po2_jmp.repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.po2_jmp.domain.BuildingNumber;
import org.po2_jmp.domain.CityName;
import org.po2_jmp.domain.PostalCode;
import org.po2_jmp.entity.Address;

class DbHotelAddressRepositoryTest {

    private final String url = "jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1";
    private final String user = "sa";
    private final String password = "";
    private DbHotelAddressRepository hotelAddressRepository;

    @BeforeEach
    public void setUp() throws SQLException {
        this.hotelAddressRepository = new DbHotelAddressRepository(
                url, user, password);
        prepareTable();
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenUrlIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
           new DbHotelAddressRepository(null, user, password);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenUserIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DbHotelAddressRepository(url, null, password);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPasswordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DbHotelAddressRepository(url, user, null);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenAllParamsAreNotNulls() {
        DbHotelAddressRepository repository = new DbHotelAddressRepository(
                url, user, password);
        assertNotNull(repository);
    }

    @Test
    void FindById_ShouldReturnPresentOptional_WhenIdIs2() throws SQLException {
        fillHotelAddressesTable();
        Optional<Address> optionalAddress = hotelAddressRepository.findById(2);
        assertTrue(optionalAddress.isPresent());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenIdIs6() throws SQLException {
        fillHotelAddressesTable();
        Optional<Address> optionalAddress = hotelAddressRepository.findById(6);
        assertTrue(optionalAddress.isEmpty());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenIdIsZero() throws SQLException {
        fillHotelAddressesTable();
        Optional<Address> optionalAddress = hotelAddressRepository.findById(0);
        assertTrue(optionalAddress.isEmpty());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenIdIsNegative() throws SQLException {
        fillHotelAddressesTable();
        Optional<Address> optionalAddress = hotelAddressRepository.findById(-1);
        assertTrue(optionalAddress.isEmpty());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenTableIsEmpty() {
        Optional<Address> optionalAddress = hotelAddressRepository.findById(1);
        assertTrue(optionalAddress.isEmpty());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenIdIsVeryLarge() throws SQLException {
        fillHotelAddressesTable();
        Optional<Address> optionalAddress = hotelAddressRepository
                .findById(Integer.MAX_VALUE);
        assertTrue(optionalAddress.isEmpty());
    }

    @Test
    void FindById_ShouldReturnCorrectAddress_WhenIdIs1() throws SQLException {
        fillHotelAddressesTable();
        Optional<Address> optionalAddress = hotelAddressRepository.findById(1);
        assertTrue(optionalAddress.isPresent());
        Address address = optionalAddress.get();
        assertEquals("Warszawa", address.getCity().getValue());
        assertEquals("00-021", address.getPostalCode().getValue());
        assertEquals("15", address.getBuildingNumber().getValue());
    }

    @Test
    void FindById_ShouldReturnCorrectAddress_WhenIdIs3() throws SQLException {
        fillHotelAddressesTable();
        Optional<Address> optionalAddress = hotelAddressRepository.findById(3);
        assertTrue(optionalAddress.isPresent());
        Address address = optionalAddress.get();
        assertEquals("Gdańsk", address.getCity().getValue());
        assertEquals("80-011", address.getPostalCode().getValue());
        assertEquals("2", address.getBuildingNumber().getValue());
    }

    @Test
    void FindAll_ShouldReturn0Records_WhenTableIsEmpty() {
        List<Address> addresses = hotelAddressRepository.findAll();
        assertEquals(0, addresses.size());
    }

    @Test
    void FindAll_ShouldReturn3Records_When3RecordsInTheTable() throws SQLException {
        fillHotelAddressesTable();
        List<Address> addresses = hotelAddressRepository.findAll();
        assertEquals(3, addresses.size());
    }

    @Test
    void FindAll_ShouldReturnCorrectRecords_When3RecordsInTheTable() throws SQLException {
        List<String> cities = List.of("Warszawa", "Kraków", "Gdańsk");
        List<String> postalCodes = List.of("00-021", "30-134", "80-011");
        List<String> buildingNumbers = List.of("15", "21A", "2");

        fillHotelAddressesTable();
        for (int i = 1; i < 4; ++i) {
            Optional<Address> optionalAddress = hotelAddressRepository.findById(i);
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
        fillHotelAddressesTable();
        Address address = new Address(
                new CityName("Kielce"),
                new PostalCode("25-751"),
                new BuildingNumber("13"));
        Optional<Integer> optionalAddressId = hotelAddressRepository.add(address);
        assertTrue(optionalAddressId.isPresent());
    }

    @Test
    void Add_ShouldReturnPresentOptionalContainingIdEqual4_When3RecordsInTheTable()
            throws SQLException {
        fillHotelAddressesTable();
        Address address = new Address(
                new CityName("Kielce"),
                new PostalCode("25-751"),
                new BuildingNumber("13"));
        Optional<Integer> optionalAddressId = hotelAddressRepository.add(address);
        assertTrue(optionalAddressId.isPresent());
        assertEquals(4, optionalAddressId.get());
    }

    private void prepareTable() throws SQLException {
        String dropSql = "DROP TABLE hotel_addresses IF EXISTS;";
        String createSql = "CREATE TABLE hotel_addresses (" +
                "   hotel_address_id SERIAL PRIMARY KEY," +
                "   city VARCHAR(60) NOT NULL," +
                "   postal_code VARCHAR(6) NOT NULL," +
                "   building_number VARCHAR(10) NOT NULL);";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement stmt = connection.prepareStatement(dropSql)) {
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = connection.prepareStatement(createSql)) {
                stmt.executeUpdate();
            }
        }
    }

    private void fillHotelAddressesTable() throws SQLException {
        String insertSql = "INSERT INTO hotel_addresses (city, postal_code," +
                "   building_number) VALUES" +
                "   ('Warszawa', '00-021', '15')," +
                "   ('Kraków', '30-134', '21A')," +
                "   ('Gdańsk', '80-011', '2');";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(insertSql)) {
            stmt.executeUpdate();
        }
    }

}