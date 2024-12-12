package org.po2_jmp.repository;

import org.po2_jmp.entity.HotelRoom;
import java.util.List;
import java.util.Optional;

public interface HotelRoomRepository {

    Optional<HotelRoom> findById(int id);
    List<HotelRoom> findAllByHotelId(int hotelId);
    Optional<Integer> add(HotelRoom room);

}
