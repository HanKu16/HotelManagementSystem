package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.HotelDescription;
import org.po2_jmp.domain.HotelName;

@Getter
public class Hotel {

    private final int id;
    private final HotelName name;
    private final HotelDescription description;
    private final Address address;

    public Hotel(HotelName name, HotelDescription description, Address address) {
        this(0, name, description, address);
    }

    public Hotel(int id, HotelName name, HotelDescription description,
                 Address address) {
        if (areAnyNullParams(name, description, address)) {
            throw new IllegalArgumentException("Hotel name, description, " +
                    "building number can not be passed as null to Hotel " +
                    "constructor");
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
    }

    private boolean areAnyNullParams(HotelName name,
            HotelDescription description, Address address) {
        return (name == null) || (description == null) ||
                (address == null);
    }

}
