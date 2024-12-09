package org.po2_jmp;

import org.po2_jmp.entity.Address;
import org.po2_jmp.repository.DbHotelAddressRepository;
import org.po2_jmp.repository.HotelAddressRepository;

import java.util.List;
import java.util.Optional;

public class BackendApp {

    public void run() {
        String URL = "jdbc:postgresql://localhost:5432/hotel_management_system_db";
        String USER = "postgres";
        String PASSWORD = "1234";

        HotelAddressRepository hotelRepository =
                new DbHotelAddressRepository(URL, USER, PASSWORD);
        Optional<Address> address1 = hotelRepository.findById(3);
        Optional<Address> address2 = hotelRepository.findById(8);
        List<Address> addresses = hotelRepository.findAll();
        System.out.println("GG");
    }

}
