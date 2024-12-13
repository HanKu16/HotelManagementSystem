package org.po2_jmp.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.po2_jmp.domain.RoleName;

@Getter
@ToString
@EqualsAndHashCode
public class Role {

    private final int id;
    private final RoleName name;

    public Role(int id, RoleName name) {
        if (name == null) {
            throw new IllegalArgumentException("RoleName can not be null," +
                    "but null was passed to Role constructor");
        }
        this.id = id;
        this.name = name;
    }

}
