package org.po2_jmp.repository.contract;

import java.util.List;
import java.util.Optional;
import org.po2_jmp.entity.HotelAmenity;

public interface HotelAmenitiesRepository {

    Optional<HotelAmenity> findById(int id);
    List<HotelAmenity> findAllByHotelId(int hotelId);
    Optional<Integer> add(HotelAmenity amenity);

}
