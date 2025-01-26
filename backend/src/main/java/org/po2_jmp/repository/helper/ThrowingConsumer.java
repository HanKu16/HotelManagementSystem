package org.po2_jmp.repository.helper;

import java.sql.SQLException;

/**
 * A functional interface that represents an operation that accepts a single input
 * argument and may throw a {@link SQLException}.
 * @param <T> the type of the input to the operation
 */
@FunctionalInterface
public interface ThrowingConsumer<T> {

    /**
     * Performs the operation on the given input.
     *
     * @param t the input argument
     * @throws SQLException if an SQL exception occurs during the operation
     */
    void accept(T t) throws SQLException;

}
