package org.po2_jmp.repository.contract;

import org.po2_jmp.entity.Reservation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationsRepository {

    Optional<Reservation> findById(int id);
    List<Reservation> findAllByUserId(String userId);
    Optional<Reservation> findByRoomIdAndReservationDate(
            int roomId, LocalDate reservationDate);
    Optional<Integer> add(Reservation reservation);
    boolean deleteById(int id);
}
