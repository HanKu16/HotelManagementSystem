package org.po2_jmp.entity;

import org.junit.jupiter.api.Test;
import org.po2_jmp.domain.RoleName;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void Constructor_ShouldThrowIllegalArgumentException_WhenRoleNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Role(5, null);
        });
    }

    @Test
    void Constructor_ShouldCreateObject_WhenRoleNameIsUser() {
        Role role = new Role(3, RoleName.USER);
        assertNotNull(role);
    }

    @Test
    void Constructor_ShouldCreateObject_WhenRoleNameIsAdmin() {
        Role role = new Role(3, RoleName.ADMIN);
        assertNotNull(role);
    }

}