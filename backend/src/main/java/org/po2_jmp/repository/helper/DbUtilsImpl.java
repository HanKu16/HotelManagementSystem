package org.po2_jmp.repository.helper;

import java.sql.*;
import java.util.Collection;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DbUtilsImpl implements DbUtils {

    private static final Logger LOGGER = LogManager.getLogger(DbUtilsImpl.class);
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
            handleSqlException(e);
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
            handleSqlException(e);
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
            handleSqlException(e);
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
            handleSqlException(e);
        }
        return false;
    }

    private void handleSqlException(SQLException e) {
        LOGGER.error("An error occurred:", e);
    }

}
