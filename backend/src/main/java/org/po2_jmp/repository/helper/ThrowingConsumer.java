package org.po2_jmp.repository.helper;

import java.sql.SQLException;

@FunctionalInterface
public interface ThrowingConsumer<T> {

    void accept(T t) throws SQLException;

}
