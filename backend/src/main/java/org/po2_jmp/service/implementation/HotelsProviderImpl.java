package org.po2_jmp.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.po2_jmp.entity.Address;
import org.po2_jmp.entity.Hotel;
import org.po2_jmp.entity.HotelAmenity;
import org.po2_jmp.entity.HotelRoom;
import org.po2_jmp.repository.contract.HotelAmenitiesRepository;
import org.po2_jmp.repository.contract.HotelRoomsRepository;
import org.po2_jmp.repository.contract.HotelsRepository;
import org.po2_jmp.request.HotelProfileRequest;
import org.po2_jmp.request.HotelsOverviewsRequest;
import org.po2_jmp.response.*;
import org.po2_jmp.service.contract.HotelsProvider;

/**
 * The {@code HotelsProviderImpl} class implements the {@link HotelsProvider} interface.
 * It is responsible for providing hotel profile and overview data from various repositories.
 *
 * <p>This class uses the {@link HotelsRepository}, {@link HotelAmenitiesRepository}, and
 * {@link HotelRoomsRepository} to fetch relevant hotel data.</p>
 */
public class HotelsProviderImpl implements HotelsProvider {

    private final HotelsRepository hotelsRepository;
    private final HotelAmenitiesRepository hotelAmenitiesRepository;
    private final HotelRoomsRepository hotelRoomsRepository;

    /**
     * Constructs a new {@code HotelsProviderImpl} instance with the specified repositories.
     *
     * @param hotelsRepository The repository used to fetch hotel data.
     * @param hotelAmenitiesRepository The repository used to fetch hotel amenities data.
     * @param hotelRoomsRepository The repository used to fetch hotel room data.
     * @throws IllegalArgumentException if any of the repositories are {@code null}.
     */
    public HotelsProviderImpl(
            HotelsRepository hotelsRepository,
            HotelAmenitiesRepository hotelAmenitiesRepository,
            HotelRoomsRepository hotelRoomsRepository) {
        if (hotelsRepository == null) {
            throw new IllegalArgumentException("HotelsRepository can not be null," +
                    " but null was passed to HotelsProviderImpl");
        }
        if (hotelAmenitiesRepository == null) {
            throw new IllegalArgumentException("HotelAmenitiesRepository " +
                    "can not be null, but null was passed to HotelsProviderImpl");
        }
        if (hotelRoomsRepository == null) {
            throw new IllegalArgumentException("HotelRoomsRepository " +
                    "can not be null, but null was passed to HotelsProviderImpl");
        }
        this.hotelsRepository = hotelsRepository;
        this.hotelAmenitiesRepository = hotelAmenitiesRepository;
        this.hotelRoomsRepository = hotelRoomsRepository;
    }

    /**
     * Retrieves the profile of a specific hotel based on the provided request.
     *
     * @param request The {@link HotelProfileRequest} containing the
     *        hotel ID to fetch data for.
     * @return A {@link HotelProfileResponse} containing hotel data
     *        or a not found response if the hotel doesn't exist.
     */
    @Override
    public HotelProfileResponse getProfile(HotelProfileRequest request) {
        int hotelId = request.getHotelId();
        Optional<Hotel> optionalHotel = hotelsRepository.findById(hotelId);
        if (optionalHotel.isEmpty()) {
            return new HotelProfileResponse(ResponseStatus.NOT_FOUND,
                    "Hotel of given id was not found");
        }
        Hotel hotel = optionalHotel.get();
        List<HotelAmenity> amenities = hotelAmenitiesRepository.findAllByHotelId(hotelId);
        List<HotelRoom> hotelRooms = hotelRoomsRepository.findAllByHotelId(hotelId);
        List<Integer> guestCapacities = getGuestCapacities(hotelRooms);
        return buildSuccessHotelProfileResponse(hotel,
                hotel.getAddress(), amenities, guestCapacities);
    }

    /**
     * Retrieves a list of overviews for all hotels in the system.
     *
     * @param request The {@link HotelsOverviewsRequest} used to initiate the overview retrieval.
     * @return A {@link HotelsOverviewsResponse} containing a list of hotel overviews.
     */
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
            Hotel hotel, Address address, List<HotelAmenity> amenities,
            List<Integer> guestCapacities) {
        List<String> amenitiesNames = getAmenitiesNames(amenities);
        return new HotelProfileResponse(
                ResponseStatus.OK,
                "Hotel data fetched successfully",
                hotel.getId(),
                hotel.getName().getValue(),
                hotel.getDescription().getValue(),
                amenitiesNames,
                mapAddressToDto(address),
                guestCapacities);
    }

    private List<String> getAmenitiesNames(List<HotelAmenity> amenities) {
        return amenities.stream()
                .map(amenity -> amenity.getName().getValue())
                .toList();
    }

    private List<Integer> getGuestCapacities(List<HotelRoom> rooms) {
        return rooms.stream()
                .map(hr -> hr.getGuestCapacity().getValue())
                .distinct()
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
