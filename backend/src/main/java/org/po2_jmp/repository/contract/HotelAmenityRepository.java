package org.po2_jmp.repository;

import java.util.List;
import java.util.Optional;
import org.po2_jmp.entity.HotelAmenity;

public interface HotelAmenityRepository {

    Optional<HotelAmenity> findById(int id);
    List<HotelAmenity> findAllByHotelId(int hotelId);

}
