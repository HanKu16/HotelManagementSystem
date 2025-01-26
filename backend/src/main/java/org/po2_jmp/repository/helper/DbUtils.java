package org.po2_jmp.repository.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Optional;

/**
 * Provides utility methods for interacting with a database using SQL queries.
 * <p>
 * The {@link DbUtils} interface defines a set of methods to execute SQL queries, inserts,
 * and deletions while handling `PreparedStatement` and `ResultSet`. These methods
 * use functional programming concepts to enable customized logic for working with
 * database records.
 * </p>
 */
public interface DbUtils {

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
    <T> Optional<T> executeQuery(String sql,
            ThrowingConsumer<PreparedStatement> paramSetter,
            ThrowingFunction<ResultSet, T> resultProcessor);

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
    <T, C extends Collection<T>> C executeQueryForCollection(
            String sql, ThrowingConsumer<PreparedStatement> paramSetter,
            ThrowingFunction<ResultSet, T> resultProcessor, C collection);

    /**
     * Executes a SQL insert statement and processes the result into an object.
     *
     * @param <T> the type of the result
     * @param sql the SQL insert statement to execute
     * @param paramSetter a function to set parameters on the {@link PreparedStatement}
     * @param resultProcessor a function to process the {@link ResultSet} and return the result
     * @return an {@link Optional} containing the result of the insert operation,
     *         or an empty {@link Optional} if the insert was unsuccessful
     */
    <T> Optional<T> executeInsert(String sql,
            ThrowingConsumer<PreparedStatement> paramSetter,
            ThrowingFunction<ResultSet, T> resultProcessor);

    /**
     * Executes a SQL delete statement.
     *
     * @param sql the SQL delete statement to execute
     * @param paramSetter a function to set parameters on the {@link PreparedStatement}
     * @return the number of rows affected by the delete operation
     */
    int executeDelete(String sql, ThrowingConsumer<PreparedStatement> paramSetter);

}
