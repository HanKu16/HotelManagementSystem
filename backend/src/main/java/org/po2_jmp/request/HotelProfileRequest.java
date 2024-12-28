package org.po2_jmp.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
public class HotelProfileRequest extends Request {

    private final int hotelId;

    @JsonCreator
    public HotelProfileRequest(
            @JsonProperty("command") String command,
            @JsonProperty("hotelId") int hotelId) {
        super(command);
        this.hotelId = hotelId;
    }

}
