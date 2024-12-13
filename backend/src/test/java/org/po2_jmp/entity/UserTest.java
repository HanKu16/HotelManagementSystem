package org.po2_jmp.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.po2_jmp.domain.UserLogin;
import org.po2_jmp.domain.UserPassword;
import java.time.LocalDateTime;

class UserTest {

    private final UserLogin validLogin = new UserLogin("ValidLogin");
    private final UserPassword validPassword = new UserPassword("validPassword");
    private final LocalDateTime validCreationDateTime = LocalDateTime.of(
            2024, 12, 13, 15, 30, 45);
    private final int validRoleId = 1;

    @Test
    void ConstructorWithoutCreationDateTime_ThrowsIllegalArgumentException_WhenLoginIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
           new User(null, validPassword, validRoleId);
        });
    }

    @Test
    void ConstructorWithoutCreationDateTime_ThrowsIllegalArgumentException_WhenPasswordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(validLogin, null, validRoleId);
        });
    }

    @Test
    void ConstructorWithoutCreationDateTime_ShouldCreateObject_WhenParamsAreValid() {
        User user = new User(validLogin, validPassword, validRoleId);
        assertNotNull(user);
    }

    @Test
    void ConstructorWithCreationDateTime_ThrowsIllegalArgumentException_WhenLoginIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(null, validPassword, validCreationDateTime,validRoleId);
        });
    }

    @Test
    void ConstructorWithCreationDateTime_ThrowsIllegalArgumentException_WhenPasswordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(validLogin, null, validCreationDateTime, validRoleId);
        });
    }

    @Test
    void ConstructorWithCreationDateTime_ThrowsIllegalArgumentException_WhenCreationDateTimeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(validLogin, validPassword, null, validRoleId);
        });
    }

    @Test
    void ConstructorWithCreationDateTime_ShouldCreateObject_WhenParamsAreValid() {
        User user = new User(validLogin, validPassword, validCreationDateTime, validRoleId);
        assertNotNull(user);
    }

}