package org.po2_jmp.repository.implementation;

import org.po2_jmp.domain.UserLogin;
import org.po2_jmp.domain.UserPassword;
import org.po2_jmp.entity.User;
import org.po2_jmp.repository.contract.UsersRepository;
import java.sql.*;
import java.util.Optional;

public class DbUsersRepository implements UsersRepository {

    private final String url;
    private final String user;
    private final String password;

    public DbUsersRepository(String url, String user, String password) {
        if (areAnyNullParams(url, user, password)) {
            throw new IllegalArgumentException("Url, user and password can not be " +
                    "nulls but were passed to DbUsersRepository constructor");
        }
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Optional<User> findById(String id) {
        String sql = "SELECT user_id, password, creation_datetime, role_id" +
                    " FROM users" +
                    " WHERE user_id = ?;";
        Optional<User> optionalUser = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    optionalUser = Optional.of(createUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalUser;
    }

    @Override
    public Optional<String> add(User newUser) {
        String sql = "INSERT INTO users (user_id, password, creation_datetime, role_id)" +
                    " VALUES (?, ?, ?, ?);";
        Optional<String> optionalUserId = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {
            setInsertQueryParams(stmt, newUser);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        optionalUserId = Optional.of(
                                rs.getString("user_id"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalUserId;
    }

    private User createUser(ResultSet rs) throws SQLException {
        return new User(
                new UserLogin(rs.getString("user_id")),
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

    private boolean areAnyNullParams(String url,
            String user, String password) {
        return (url == null) || (user == null) || (password == null);
    }

}
