package org.po2_jmp.service.contract;

import org.po2_jmp.request.HotelProfileRequest;
import org.po2_jmp.request.HotelsOverviewsRequest;
import org.po2_jmp.response.HotelProfileResponse;
import org.po2_jmp.response.HotelsOverviewsResponse;

public interface HotelsProvider {

    HotelProfileResponse getProfile(HotelProfileRequest request);
    HotelsOverviewsResponse getHotelsOverviews(HotelsOverviewsRequest request);

}

