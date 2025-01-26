package org.po2_jmp.repository.helper;

import java.sql.SQLException;

/**
 * A functional interface that represents a function that accepts an input of type {@code T}
 * and produces a result of type {@code R}, while potentially throwing a {@link SQLException}.
*
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface ThrowingFunction<T, R> {

    /**
     * Applies the function to the given input.
     *
     * @param t the input argument
     * @return the result of applying the function
     * @throws SQLException if an SQL exception occurs during the operation
     */
    R apply(T t) throws SQLException;

}
