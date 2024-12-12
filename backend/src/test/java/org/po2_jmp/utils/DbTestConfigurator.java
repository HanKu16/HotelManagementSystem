package org.po2_jmp.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbTestConfigurator {

    private static final String BASE_TEST_SCRIPT_PATH = "src/test/resources/scripts/";
    private final String url;
    private final String user;
    private final String password;
    private final String dropSql;
    private final String createSql;
    private final String insertSql;

    public DbTestConfigurator(String url, String user, String password,
            String tableName) throws IOException {
        this.url = url;
        this.user = user;
        this.password = password;
        this.dropSql = readScript(getDropScriptPath(tableName));
        this.createSql = readScript(getCreateScriptPath(tableName));
        this.insertSql = readScript(getInsertScriptPath(tableName));
    }

    public void drop() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(dropSql)) {
            stmt.executeUpdate();
        }
    }

    public void create() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(createSql)) {
            stmt.executeUpdate();
        }
    }

    public void insert() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(insertSql)) {
            stmt.executeUpdate();
        }
    }

    private String readScript(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }

    private String getDropScriptPath(String tableName) {
        return BASE_TEST_SCRIPT_PATH + "drop/" + tableName + ".txt";
    }

    private String getCreateScriptPath(String tableName) {
        return BASE_TEST_SCRIPT_PATH + "create/" + tableName + ".txt";
    }

    private String getInsertScriptPath(String tableName) {
        return BASE_TEST_SCRIPT_PATH + "insert/" + tableName + ".txt";
    }

}
