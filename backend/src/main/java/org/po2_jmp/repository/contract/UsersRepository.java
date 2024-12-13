package org.po2_jmp.repository.contract;

import org.po2_jmp.entity.User;
import java.util.Optional;

public interface UsersRepository {

    Optional<User> findById(String id);
    Optional<String> add(User user);

}
