package org.po2_jmp.repository.implementation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.po2_jmp.domain.UserLogin;
import org.po2_jmp.domain.UserPassword;
import org.po2_jmp.entity.User;
import org.po2_jmp.utils.DbTestConfigurator;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

class DbUsersRepositoryTest {

    private final String url = "jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1";
    private final String user = "sa";
    private final String password = "";
    private final DbTestConfigurator usersConfigurator = createConfigurator("users");
    private final DbTestConfigurator rolesConfigurator = createConfigurator("roles");
    private DbUsersRepository usersRepository;

    @BeforeEach
    public void setUp() throws SQLException {
        this.usersRepository = new DbUsersRepository(url, user, password);
        rolesConfigurator.create();
        usersConfigurator.create();
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        usersConfigurator.drop();
        rolesConfigurator.drop();
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenUrlIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DbUsersRepository(null, user, password);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenUserIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DbUsersRepository(url, null, password);
        });
    }

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenPasswordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DbUsersRepository(url, user, null);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenAllParamsAreNotNulls() {
        DbUsersRepository repository = new DbUsersRepository(url, user, password);
        assertNotNull(repository);
    }

    @Test
    void FindById_ShouldReturnPresentOptional_WhenIdIsSzpaku() throws SQLException {
        insertData();
        Optional<User> optionalUser = usersRepository.findById("szpaku");
        assertTrue(optionalUser.isPresent());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenIdIsPiotrek12() throws SQLException {
        insertData();
        Optional<User> optionalUser = usersRepository.findById("Piotrek12");
        assertTrue(optionalUser.isEmpty());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenTableIsEmpty() {
        Optional<User> optionalUser = usersRepository.findById("szpaku");
        assertTrue(optionalUser.isEmpty());
    }

    @Test
    void FindById_ShouldReturnCorrectUser_WhenIdIsAdrian16() throws SQLException {
        insertData();
        Optional<User> optionalUser = usersRepository.findById("adrian16");
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertEquals(new UserLogin("adrian16"), user.getId());
        assertEquals(new UserPassword("piesPimpek12"), user.getPassword());
        assertEquals(1, user.getRoleId());
    }

    @Test
    void Add_ShouldReturnPresentOptional_WhenUserIsValidAndTableIsNotEmpty()
            throws SQLException {
        insertData();
        User user = new User(
                new UserLogin("nowyUser"),
                new UserPassword("hasloUzytkownika"),
                LocalDateTime.now(),
                1
        );
        Optional<String> optionalUserId = usersRepository.add(user);
        assertTrue(optionalUserId.isPresent());
    }

    @Test
    void Add_ShouldReturnPresentOptional_WhenUserIsValidAndTableIsEmpty()
            throws SQLException {
        rolesConfigurator.insert();
        User user = new User(
                new UserLogin("nowyUser"),
                new UserPassword("hasloUzytkownika"),
                LocalDateTime.now(),
                1
        );
        Optional<String> optionalUserId = usersRepository.add(user);
        assertTrue(optionalUserId.isPresent());
    }

    private void insertData() throws SQLException {
        rolesConfigurator.insert();
        usersConfigurator.insert();
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