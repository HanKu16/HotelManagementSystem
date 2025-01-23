package org.po2_jmp.repository.implementation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.po2_jmp.entity.Role;
import org.po2_jmp.utils.DbTestConfigurator;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

class DbRolesRepositoryTest {

    private final String url = "jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1";
    private final String user = "sa";
    private final String password = "";
    private final DbUtils dbUtils = new DbUtils(url, user, password);
    private final DbTestConfigurator usersConfigurator = createConfigurator("users");
    private final DbTestConfigurator rolesConfigurator = createConfigurator("roles");
    private DbRolesRepository rolesRepository;

    @BeforeEach
    public void setUp() throws SQLException {
        this.rolesRepository = new DbRolesRepository(dbUtils);
        rolesConfigurator.create();
        usersConfigurator.create();
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        usersConfigurator.drop();
        rolesConfigurator.drop();
    }

    @Test
    void FindById_ShouldReturnPresentOptional_WhenIdIs1() throws SQLException {
        insertData();
        Optional<Role> optionalRole = rolesRepository.findById(1);
        assertTrue(optionalRole.isPresent());
    }

    @Test
    void FindById_ShouldReturnPresentOptional_WhenIdIs2() throws SQLException {
        insertData();
        Optional<Role> optionalRole = rolesRepository.findById(2);
        assertTrue(optionalRole.isPresent());
    }

    @Test
    void FindById_ShouldReturnEmptyOptional_WhenTableIsEmpty() throws SQLException {
        Optional<Role> optionalRole = rolesRepository.findById(2);
        assertTrue(optionalRole.isEmpty());
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