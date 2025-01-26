package org.po2_jmp.repository.implementation;

import org.po2_jmp.domain.UserId;
import org.po2_jmp.domain.UserPassword;
import org.po2_jmp.entity.User;
import org.po2_jmp.repository.contract.UsersRepository;
import org.po2_jmp.repository.helper.DbUtils;
import java.sql.*;
import java.util.Optional;

/**
 * Implementation of the {@link UsersRepository} interface using a relational database.
 * This class provides methods for retrieving and adding users from/to the database.
 * <p>
 * The {@link DbUsersRepository} uses the {@link DbUtils} utility class to execute
 * SQL queries and interact with the database.
 * </p>
 */
public class DbUsersRepository implements UsersRepository {

    private final DbUtils dbUtils;

    /**
     * Constructs a {@link DbUsersRepository} with the specified {@link DbUtils} utility.
     *
     * @param dbUtils the {@link DbUtils} utility for executing SQL queries
     * @throws IllegalArgumentException if {@code dbUtils} is {@code null}
     */
    public DbUsersRepository(DbUtils dbUtils) {
        if (dbUtils == null) {
            throw new IllegalArgumentException("DbUtils can not be null " +
                    "but null was passed to DbUsersRepository constructor");
        }
        this.dbUtils = dbUtils;
    }

    /**
     * Finds a user by their unique ID.
     *
     * @param id the ID of the user to find
     * @return an {@link Optional} containing the {@link User} if found, or
     * {@link Optional#empty()} if not
     */
    @Override
    public Optional<User> findById(String id) {
        String sql = "SELECT user_id, password, creation_datetime, role_id" +
                    " FROM users" +
                    " WHERE user_id = ?;";
        return dbUtils.executeQuery(sql,
                stmt -> stmt.setString(1, id),
                this::createUser);
    }

    /**
     * Adds a new user to the database.
     *
     * @param user the {@link User} to add
     * @return an {@link Optional} containing the ID of the new user if added successfully,
     * or {@link Optional#empty()} if not
     */
    @Override
    public Optional<String> add(User user) {
        String sql = "INSERT INTO users (user_id, password, creation_datetime, role_id)" +
                    " VALUES (?, ?, ?, ?);";
        return dbUtils.executeInsert(sql,
                stmt -> setInsertQueryParams(stmt, user),
                rs -> rs.getString("user_id"));
    }

    private User createUser(ResultSet rs) throws SQLException {
        return new User(
                new UserId(rs.getString("user_id")),
                new UserPassword(rs.getString("password")),
                rs.getTimestamp("creation_datetime").toLocalDateTime(),
                rs.getInt("role_id")
        );
    }

    private void setInsertQueryParams(PreparedStatement stmt, User user)
            throws SQLException {
        stmt.setString(1, user.getId().getValue());
        stmt.setString(2, user.getPassword().getValue());
        stmt.setTimestamp(3, Timestamp.valueOf(user.getCreationDateTime()));
        stmt.setInt(4, user.getRoleId());
    }

}
