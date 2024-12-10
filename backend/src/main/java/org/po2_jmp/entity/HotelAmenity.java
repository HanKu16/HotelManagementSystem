package org.po2_jmp.entity;

import lombok.Getter;
import org.po2_jmp.domain.HotelAmenityName;

@Getter
public class HotelAmenity {

    private final int id;
    private final HotelAmenityName name;
    private final int hotelId;

    public HotelAmenity(HotelAmenityName name, int hotelId) {
        this(0, name, hotelId);
    }

    public HotelAmenity(int id, HotelAmenityName amenityName, int hotelId) {
        if (amenityName == null) {
            throw new IllegalArgumentException("Hotel amenity name can not be null" +
                    "but null was passed to HotelAmenity constructor");
        }
        this.id = id;
        this.name = amenityName;
        this.hotelId = hotelId;
    }

}