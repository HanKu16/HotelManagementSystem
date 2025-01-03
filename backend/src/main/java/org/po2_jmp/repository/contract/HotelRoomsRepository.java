package org.po2_jmp.repository.contract;

import org.po2_jmp.entity.HotelRoom;
import java.util.List;
import java.util.Optional;

public interface HotelRoomsRepository {

    Optional<HotelRoom> findById(int id);
    List<HotelRoom> findAllByHotelId(int hotelId);
    List<HotelRoom> findAllByHotelIdAndGuestCapacity(int hotelId, int guestCapacity);
    Optional<Integer> add(HotelRoom room);

}
