package org.po2_jmp.repository.helper;

import java.sql.SQLException;

@FunctionalInterface
public interface ThrowingFunction<T, R> {

    R apply(T t) throws SQLException;

}
