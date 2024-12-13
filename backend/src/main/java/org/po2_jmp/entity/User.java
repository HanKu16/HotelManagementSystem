package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.*;
import java.time.LocalDateTime;

@Getter
public class User {

    private final UserLogin id;
    private final UserPassword password;
    private final LocalDateTime creationDateTime;
    private final int roleId;

    public User(UserLogin id, UserPassword password, int roleId) {
        this(id, password, LocalDateTime.now(), roleId);
    }

    public User(UserLogin id, UserPassword password,
            LocalDateTime creationDateTime, int roleId) {
        if (areAnyNullParams(id, password, creationDateTime)) {
            throw new IllegalArgumentException("User id, password and " +
                    "creation datetime can not be nulls, but null was " +
                    "passed to User constructor");
        }
        this.id = id;
        this.password = password;
        this.creationDateTime = creationDateTime;
        this.roleId = roleId;
    }

    private boolean areAnyNullParams(UserLogin id, UserPassword password,
            LocalDateTime creationDateTime) {
        return (id == null) || (password == null) ||
                (creationDateTime == null);
    }

}