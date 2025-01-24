package org.po2_jmp.repository.helper;

import java.sql.*;
import java.util.Collection;
import java.util.Optional;

public class DbUtilsImpl implements DbUtils {

    private final String url;
    private final String user;
    private final String password;

    public DbUtilsImpl(String url, String user, String password) {
        if ((url == null) || (user == null) || (password == null)) {
            throw new IllegalArgumentException("Url, user, " +
                    "and password cannot be null, but null " +
                    "was passed to DbUtilsImpl constructor");
        }
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public <T> Optional<T> executeQuery(String sql,
            ThrowingConsumer<PreparedStatement> paramSetter,
            ThrowingFunction<ResultSet, T> resultProcessor) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            paramSetter.accept(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(resultProcessor.apply(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public <T, C extends Collection<T>> C executeQueryForCollection(
            String sql, ThrowingConsumer<PreparedStatement> paramSetter,
            ThrowingFunction<ResultSet, T> resultProcessor, C collection) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            paramSetter.accept(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    T item = resultProcessor.apply(rs);
                    collection.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return collection;
    }

    public <T> Optional<T> executeInsert(String sql,
            ThrowingConsumer<PreparedStatement> paramSetter,
            ThrowingFunction<ResultSet, T> resultProcessor) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            paramSetter.accept(stmt);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return Optional.ofNullable(resultProcessor.apply(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean executeDelete(String sql,
            ThrowingConsumer<PreparedStatement> paramSetter) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            paramSetter.accept(stmt);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
