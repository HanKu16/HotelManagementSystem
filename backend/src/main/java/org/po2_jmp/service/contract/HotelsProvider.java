package org.po2_jmp.service.contract;

import org.po2_jmp.request.HotelProfileRequest;
import org.po2_jmp.request.HotelsOverviewsRequest;
import org.po2_jmp.response.HotelProfileResponse;
import org.po2_jmp.response.HotelsOverviewsResponse;

/**
 * The {@code HotelsProvider} interface defines the contract for retrieving hotel data.
 * It provides methods for getting the profile of a specific hotel and for retrieving
 * overviews of multiple hotels.
 *
 * <p>Implementations of this interface are responsible for providing the logic to
 * fetch and return hotel profile and overview data.</p>
 */
public interface HotelsProvider {

    /**
     * Retrieves the profile of a specific hotel.
     *
     * @param request The {@link HotelProfileRequest} containing the hotel profile request data.
     * @return A {@link HotelProfileResponse} containing the profile data for the hotel.
     */
    HotelProfileResponse getProfile(HotelProfileRequest request);

    /**
     * Retrieves an overview of multiple hotels.
     *
     * @param request The {@link HotelsOverviewsRequest} containing the request data for hotel overviews.
     * @return A {@link HotelsOverviewsResponse} containing the overview details for the hotels.
     */
    HotelsOverviewsResponse getHotelsOverviews(HotelsOverviewsRequest request);

}

