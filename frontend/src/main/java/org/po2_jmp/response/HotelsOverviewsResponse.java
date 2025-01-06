package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelsOverviewsResponse extends Response {

    private final List<HotelOverviewDto> hotels;

    public HotelsOverviewsResponse(ResponseStatus status, String message) {
        this(status, message, null);
    }

    public HotelsOverviewsResponse(ResponseStatus status,
            String message, List<HotelOverviewDto> hotels) {
        super(status, message);
        this.hotels = hotels;
    }

}
