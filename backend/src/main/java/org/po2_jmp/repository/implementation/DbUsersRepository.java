package org.po2_jmp.repository.implementation;

import org.po2_jmp.domain.UserId;
import org.po2_jmp.domain.UserPassword;
import org.po2_jmp.entity.User;
import org.po2_jmp.repository.contract.UsersRepository;
import org.po2_jmp.repository.helper.DbUtils;
import java.sql.*;
import java.util.Optional;

public class DbUsersRepository implements UsersRepository {

    private final DbUtils dbUtils;

    public DbUsersRepository(DbUtils dbUtils) {
        if (dbUtils == null) {
            throw new IllegalArgumentException("DbUtils can not be null " +
                    "but null was passed to DbUsersRepository constructor");
        }
        this.dbUtils = dbUtils;
    }

    @Override
    public Optional<User> findById(String id) {
        String sql = "SELECT user_id, password, creation_datetime, role_id" +
                    " FROM users" +
                    " WHERE user_id = ?;";
        return dbUtils.executeQuery(sql,
                stmt -> stmt.setString(1, id),
                this::createUser);
    }

    @Override
    public Optional<String> add(User newUser) {
        String sql = "INSERT INTO users (user_id, password, creation_datetime, role_id)" +
                    " VALUES (?, ?, ?, ?);";
        return dbUtils.executeInsert(sql,
                stmt -> setInsertQueryParams(stmt, newUser),
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
