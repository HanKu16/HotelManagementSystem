package org.po2_jmp.repository.contract;

import org.po2_jmp.entity.Hotel;
import java.util.List;
import java.util.Optional;

public interface HotelsRepository {

    Optional<Hotel> findById(int id);
    List<Hotel> findAll();
    Optional<Integer> add(Hotel hotel);

}
