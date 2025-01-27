package org.po2_jmp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

/**
 * Represents the response containing an overview of multiple hotels.
 * <p>
 * This class extends {@link Response} and includes a list of {@link HotelOverviewDto}
 * objects, providing an overview of each hotel in the response.
 * </p>
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelsOverviewsResponse extends Response {

    private final List<HotelOverviewDto> hotels;


    /**
     * Constructs a {@code HotelsOverviewsResponse} with the specified status and message,
     * but without hotel details. Should be used for error responses).
     *
     * @param status the status of the response.
     * @param message the message associated with the response.
     */
    public HotelsOverviewsResponse(ResponseStatus status, String message) {
        this(status, message, null);
    }

    /**
     * Constructs a {@code HotelsOverviewsResponse} with the specified status, message,
     * and a list of hotel overviews.
     *
     * @param status the status of the response.
     * @param message the message associated with the response.
     * @param hotels a list of {@link HotelOverviewDto} representing hotel overviews.
     */
    public HotelsOverviewsResponse(ResponseStatus status,
            String message, List<HotelOverviewDto> hotels) {
        super(status, message);
        this.hotels = hotels;
    }

}
