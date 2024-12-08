package org.po2_jmp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.po2_jmp.domain.HotelDescription;
import org.po2_jmp.domain.HotelName;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    private int id;
    private HotelName name;
    private HotelDescription description;
    private Address address;

    public Hotel(HotelName name, HotelDescription description) {
        this.name = name;
        this.description = description;
    }

}
