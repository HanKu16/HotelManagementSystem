package org.po2_jmp.repository.contract;

import org.po2_jmp.entity.Role;
import java.util.Optional;

public interface RolesRepository {

    Optional<Role> findById(int id);

}
