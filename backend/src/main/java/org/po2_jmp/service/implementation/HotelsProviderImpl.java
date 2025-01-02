package org.po2_jmp.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.po2_jmp.entity.Address;
import org.po2_jmp.entity.Hotel;
import org.po2_jmp.entity.HotelAmenity;
import org.po2_jmp.repository.contract.HotelAmenitiesRepository;
import org.po2_jmp.repository.contract.HotelsRepository;
import org.po2_jmp.request.HotelProfileRequest;
import org.po2_jmp.request.HotelsOverviewsRequest;
import org.po2_jmp.response.*;
import org.po2_jmp.service.contract.HotelsProvider;

public class HotelsProviderImpl implements HotelsProvider {

    private final HotelsRepository hotelsRepository;
    private final HotelAmenitiesRepository hotelAmenitiesRepository;

    public HotelsProviderImpl(HotelsRepository hotelsRepository,
            HotelAmenitiesRepository hotelAmenitiesRepository) {
        if (hotelsRepository == null) {
            throw new IllegalArgumentException("HotelsRepository can not be null," +
                    " but null was passed to HotelsProviderImpl");
        }
        if (hotelAmenitiesRepository == null) {
            throw new IllegalArgumentException("HotelAmenitiesRepository " +
                    "can not be null, but null was passed to HotelsProviderImpl");
        }
        this.hotelsRepository = hotelsRepository;
        this.hotelAmenitiesRepository = hotelAmenitiesRepository;
    }

    @Override
    public HotelProfileResponse getProfileById(HotelProfileRequest request) {
        int hotelId = request.getHotelId();
        Optional<Hotel> optionalHotel = hotelsRepository.findById(hotelId);
        if (optionalHotel.isEmpty()) {
            return new HotelProfileResponse(ResponseStatus.NOT_FOUND,
                    "Hotel of given id was not found");
        }
        Hotel hotel = optionalHotel.get();
        List<HotelAmenity> amenities = hotelAmenitiesRepository.findAllByHotelId(hotelId);
        return buildSuccessHotelProfileResponse(hotel, hotel.getAddress(), amenities);
    }

    @Override
    public HotelsOverviewsResponse getHotelsOverviews(
            HotelsOverviewsRequest request) {
        List<Hotel> hotels = hotelsRepository.findAll();
        List<HotelOverviewDto> hotelsOverviews = collectHotelOverviewsData(hotels);
        return new HotelsOverviewsResponse(ResponseStatus.OK,
                "Hotels overviews fetched successfully", hotelsOverviews);
    }

    private List<HotelOverviewDto> collectHotelOverviewsData(List<Hotel> hotels) {
        List<HotelOverviewDto> hotelsOverviews = new ArrayList<>();
        for (Hotel hotel : hotels) {
            List<HotelAmenity> amenities = hotelAmenitiesRepository
                    .findAllByHotelId(hotel.getId());
            List<String> amenitiesNames = getAmenitiesNames(amenities);
            HotelOverviewDto dto = new HotelOverviewDto(
                    hotel.getId(),
                    hotel.getName().getValue(),
                    amenitiesNames,
                    mapAddressToDto(hotel.getAddress())
            );
            hotelsOverviews.add(dto);
        }
        return hotelsOverviews;
    }

    private HotelProfileResponse buildSuccessHotelProfileResponse(
            Hotel hotel, Address address, List<HotelAmenity> amenities) {
        List<String> amenitiesNames = getAmenitiesNames(amenities);
        return new HotelProfileResponse(
                ResponseStatus.OK,
                "Hotel data fetched successfully",
                hotel.getId(),
                hotel.getName().getValue(),
                hotel.getDescription().getValue(),
                amenitiesNames,
                mapAddressToDto(address));
    }

    private List<String> getAmenitiesNames(List<HotelAmenity> amenities) {
        return amenities.stream()
                .map(amenity -> amenity.getName().getValue())
                .toList();
    }

    private AddressDto mapAddressToDto(Address address) {
        return new AddressDto(
                address.getCity().getValue(),
                address.getStreet().getValue(),
                address.getPostalCode().getValue(),
                address.getBuildingNumber().getValue());
    }

}
