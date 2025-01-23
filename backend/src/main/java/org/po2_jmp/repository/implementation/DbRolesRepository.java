package org.po2_jmp.repository.implementation;

import java.sql.*;
import java.util.Optional;
import org.po2_jmp.domain.RoleName;
import org.po2_jmp.entity.Role;
import org.po2_jmp.repository.contract.RolesRepository;

public class DbRolesRepository implements RolesRepository {

    private final DbUtils dbUtils;

    public DbRolesRepository(DbUtils dbUtils) {
        if (dbUtils == null) {
            throw new IllegalArgumentException("DbUtils can not be " +
                    "null but null was passed to DbRolesRepository constructor");
        }
        this.dbUtils = dbUtils;
    }

    @Override
    public Optional<Role> findById(int id) {
        String sql = "SELECT role_id, name FROM roles WHERE role_id = ?;";
        return dbUtils.executeQuery(sql,
                stmt -> stmt.setInt(1, id),
                rs -> createRole(rs));
    }

    private Role createRole(ResultSet rs) throws SQLException {
        return new Role(
                rs.getInt("role_id"),
                RoleName.valueOf(rs.getString("name"))
        );
    }

}
