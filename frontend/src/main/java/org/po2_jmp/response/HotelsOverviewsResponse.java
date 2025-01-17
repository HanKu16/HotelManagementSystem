package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelsOverviewsResponse extends Response {

    private final List<HotelOverviewDto> hotels;

    @JsonCreator
    public HotelsOverviewsResponse(
            @JsonProperty("status") ResponseStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("hotels") List<HotelOverviewDto> hotels) {
        super(status, message);
        this.hotels = hotels;
    }
}
