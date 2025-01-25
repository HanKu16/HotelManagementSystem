package org.po2_jmp.repository.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Optional;

public interface DbUtils {

    <T> Optional<T> executeQuery(String sql,
            ThrowingConsumer<PreparedStatement> paramSetter,
            ThrowingFunction<ResultSet, T> resultProcessor);
    <T, C extends Collection<T>> C executeQueryForCollection(
            String sql, ThrowingConsumer<PreparedStatement> paramSetter,
            ThrowingFunction<ResultSet, T> resultProcessor, C collection);
    <T> Optional<T> executeInsert(String sql,
            ThrowingConsumer<PreparedStatement> paramSetter,
            ThrowingFunction<ResultSet, T> resultProcessor);
    int executeDelete(String sql, ThrowingConsumer<PreparedStatement> paramSetter);

}
