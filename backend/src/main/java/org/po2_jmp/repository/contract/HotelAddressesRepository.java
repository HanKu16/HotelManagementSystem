package org.po2_jmp.repository.contract;

import org.po2_jmp.entity.Address;
import java.util.List;
import java.util.Optional;

public interface HotelAddressesRepository {

    Optional<Address> findById(int id);
    List<Address> findAll();
    Optional<Integer> add(Address address);

}
