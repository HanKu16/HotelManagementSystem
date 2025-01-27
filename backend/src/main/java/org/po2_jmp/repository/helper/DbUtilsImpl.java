package org.po2_jmp.repository.helper;

import java.sql.*;
import java.util.Collection;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of the {@link DbUtils} interface for interacting with a relational database.
 * <p>
 * {@link DbUtilsImpl} provides concrete methods for executing SQL queries, inserts, and deletes
 * for a relational database. The methods handle the creation of database connections,
 * prepared statements, and result set processing while offering customizability through
 * functional interfaces for setting parameters and processing query results.
 */
public class DbUtilsImpl implements DbUtils {

    private static final Logger LOGGER = LogManager.getLogger(DbUtilsImpl.class);
    private final String url;
    private final String user;
    private final String password;

    /**
     * Constructs a new {@link DbUtilsImpl} instance with the given database connection details.
     *
     * @param url the database URL
     * @param user the username for the database connection
     * @param password the password for the database connection
     * @throws IllegalArgumentException if any of the parameters are {@code null}
     */
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


    /**
     * Executes a SQL query and processes the result into an object.
     *
     * @param <T> the type of the result
     * @param sql the SQL query to execute
     * @param paramSetter a function to set parameters on the {@link PreparedStatement}
     * @param resultProcessor a function to process the {@link ResultSet} and return the result
     * @return an {@link Optional} containing the result of the query, or an empty {@link Optional}
     *         if no result is found or if an error occurs
     */
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

    /**
     * Executes a SQL query and processes the result into a collection of objects.
     *
     * @param <T> the type of the individual items in the collection
     * @param <C> the type of the collection to return
     * @param sql the SQL query to execute
     * @param paramSetter a function to set parameters on the {@link PreparedStatement}
     * @param resultProcessor a function to process the {@link ResultSet} and return the individual item
     * @param collection an instance of the collection to be populated with results
     * @return the collection populated with the results from the query
     */
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

    /**
     * Executes a SQL insert statement and processes the result into an object.
     *
     * @param <T> the type of the result
     * @param sql the SQL insert statement to execute
     * @param paramSetter a function to set parameters on the {@link PreparedStatement}
     * @param resultProcessor a function to process the {@link ResultSet} and return the result
     * @return an {@link Optional} containing id (of entity) used in the insert operation,
     *         or an empty {@link Optional} if the insert was unsuccessful
     */
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

    /**
     * Executes a SQL delete statement.
     *
     * @param sql the SQL delete statement to execute
     * @param paramSetter a function to set parameters on the {@link PreparedStatement}
     * @return the number of rows affected by the delete operation
     */
    public int executeDelete(String sql,
            ThrowingConsumer<PreparedStatement> paramSetter) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            paramSetter.accept(stmt);
            int affectedRows = stmt.executeUpdate();
            return affectedRows;
        } catch (SQLException e) {
            handleSqlException(e);
        }
        return 0;
    }

    private void handleSqlException(SQLException e) {
        LOGGER.error("An error occurred:", e);
    }

}
