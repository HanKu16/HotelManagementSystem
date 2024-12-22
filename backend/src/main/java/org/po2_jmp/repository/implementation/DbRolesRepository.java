package org.po2_jmp.repository.implementation;

import java.sql.*;
import java.util.Optional;
import org.po2_jmp.domain.RoleName;
import org.po2_jmp.entity.Role;
import org.po2_jmp.repository.contract.RolesRepository;

public class DbRolesRepository implements RolesRepository {

    private final String url;
    private final String user;
    private final String password;

    public DbRolesRepository(String url, String user, String password) {
        if (areAnyNullParams(url, user, password)) {
            throw new IllegalArgumentException("Url, user and password can not be " +
                    "nulls but were passed to DbRolesRepository constructor");
        }
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Optional<Role> findById(int id) {
        String sql = "SELECT role_id, name FROM roles WHERE role_id = ?;";
        Optional<Role> optionalRole = Optional.empty();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Role role = createRole(rs);
                    optionalRole = Optional.of(role);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalRole;
    }

    private Role createRole(ResultSet rs) throws SQLException {
        return new Role(
                rs.getInt("role_id"),
                RoleName.valueOf(rs.getString("name"))
        );
    }

    private boolean areAnyNullParams(String url, String user, String password) {
        return (url == null) || (user == null) || (password == null);
    }

}
