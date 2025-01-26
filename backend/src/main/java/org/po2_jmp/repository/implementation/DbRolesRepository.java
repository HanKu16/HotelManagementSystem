package org.po2_jmp.repository.implementation;

import java.sql.*;
import java.util.Optional;
import org.po2_jmp.domain.RoleName;
import org.po2_jmp.entity.Role;
import org.po2_jmp.repository.contract.RolesRepository;
import org.po2_jmp.repository.helper.DbUtils;

/**
 * Implementation of the {@link RolesRepository} interface using a relational database.
 * This class provides a method to retrieve roles by their unique ID.
 * <p>
 * The {@link DbRolesRepository} utilizes the {@link DbUtils} utility class to
 * execute SQL queries and interact with the database. It provides basic operations
 * for querying role data stored in the database.
 * </p>
 */
public class DbRolesRepository implements RolesRepository {

    private final DbUtils dbUtils;

    /**
     * Constructs a {@link DbRolesRepository} with the specified {@link DbUtils} utility.
     *
     * @param dbUtils the {@link DbUtils} utility for executing SQL queries
     * @throws IllegalArgumentException if {@code dbUtils} is {@code null}
     */
    public DbRolesRepository(DbUtils dbUtils) {
        if (dbUtils == null) {
            throw new IllegalArgumentException("DbUtils can not be " +
                    "null but null was passed to DbRolesRepository constructor");
        }
        this.dbUtils = dbUtils;
    }

    /**
     * Finds a role by its unique ID.
     *
     * @param id the ID of the role to find
     * @return an {@link Optional} containing the {@link Role} if found,
     * or {@link Optional#empty()} if not
     */
    @Override
    public Optional<Role> findById(int id) {
        String sql = "SELECT role_id, name FROM roles WHERE role_id = ?;";
        return dbUtils.executeQuery(sql,
                stmt -> stmt.setInt(1, id),
                this::createRole);
    }

    private Role createRole(ResultSet rs) throws SQLException {
        return new Role(
                rs.getInt("role_id"),
                RoleName.valueOf(rs.getString("name"))
        );
    }

}
