package org.po2_jmp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.po2_jmp.domain.BuildingNumber;
import org.po2_jmp.domain.CityName;
import org.po2_jmp.domain.PostalCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private int id;
    private CityName city;
    private PostalCode postalCode;
    private BuildingNumber buildingNumber;

}
