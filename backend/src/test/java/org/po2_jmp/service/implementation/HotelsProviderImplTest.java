package org.po2_jmp.service.implementation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.po2_jmp.domain.*;
import org.po2_jmp.entity.Address;
import org.po2_jmp.entity.Hotel;
import org.po2_jmp.entity.HotelAmenity;
import org.po2_jmp.entity.HotelRoom;
import org.po2_jmp.repository.contract.HotelAmenitiesRepository;
import org.po2_jmp.repository.contract.HotelRoomsRepository;
import org.po2_jmp.repository.contract.HotelsRepository;
import org.po2_jmp.request.HotelProfileRequest;
import org.po2_jmp.request.HotelsOverviewsRequest;
import org.po2_jmp.response.HotelProfileResponse;
import org.po2_jmp.response.HotelsOverviewsResponse;
import org.po2_jmp.response.ResponseStatus;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class HotelsProviderImplTest {

    @Mock
    private HotelsRepository hotelsRepository;

    @Mock
    private HotelAmenitiesRepository hotelAmenitiesRepository;

    @Mock
    private HotelRoomsRepository hotelRoomsRepository;

    @InjectMocks
    private HotelsProviderImpl hotelsProvider;

    @ParameterizedTest
    @ValueSource(ints = {17, 100, 2005})
    void GetProfile_ShouldReturnStatusOfNotFound_WhenHotelIdOfNotExistingHotelGiven(int hotelId) {
        when(hotelsRepository.findById(hotelId)).thenReturn(Optional.empty());

        HotelProfileRequest request = new HotelProfileRequest(
                "getHotelProfile", hotelId);
        HotelProfileResponse response = hotelsProvider.getProfile(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 15, 57})
    void GetProfile_ShouldReturnStatusOfOk_WhenEverythingIsOk(int hotelId) {
        when(hotelsRepository.findById(hotelId)).thenReturn(
                Optional.of(createHotelForMock(hotelId)));
        when(hotelAmenitiesRepository.findAllByHotelId(hotelId))
                .thenReturn(createHotelAmenitiesForMock(hotelId));
        when(hotelRoomsRepository.findAllByHotelId(hotelId))
                .thenReturn(createHotelRoomsForMock(hotelId));

        HotelProfileRequest request = new HotelProfileRequest("getHotelProfile", hotelId);
        HotelProfileResponse response = hotelsProvider.getProfile(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 64, 122})
    void GetHotelsOverviews_ShouldReturnEmptyList_WhenNoHotelExist(int hotelId) {
        when(hotelsRepository.findAll()).thenReturn(List.of());

        HotelsOverviewsRequest request = new HotelsOverviewsRequest("getHotelsOverviews");
        HotelsOverviewsResponse response = hotelsProvider.getHotelsOverviews(request);

        assertEquals(0, response.getHotels().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5})
    void GetHotelsOverviews_ShouldReturnListOfNElements_WhenNHotelsExists(int numberOfHotels) {
        when(hotelsRepository.findAll()).thenReturn(createHotelsForMock(numberOfHotels));
        for (int i = 1; i <= numberOfHotels; ++i) {
            when(hotelAmenitiesRepository.findAllByHotelId(i))
                    .thenReturn(createHotelAmenitiesForMock(i));
        }

        HotelsOverviewsRequest request = new HotelsOverviewsRequest("getHotelsOverviews");
        HotelsOverviewsResponse response = hotelsProvider.getHotelsOverviews(request);

        assertEquals(numberOfHotels, response.getHotels().size());
    }

    private Hotel createHotelForMock(int hotelId) {
        return new Hotel(hotelId, new HotelName("Akropol"),
                new HotelDescription("To bardzo dobry hotel"),
                new Address(17, new CityName("Krakow"),
                        new StreetName("Debowa"), new PostalCode("25-001"),
                        new BuildingNumber("13")));
    }

    private List<Hotel> createHotelsForMock(int numberOfHotels) {
        String[] hotelNames = { "Akropol", "Kaptiol", "Olimp", "Babilon", "Proton" };
        String[] cities = { "Warszawa", "Krakow", "Kielce", "Wroclaw", "Gdansk" };
        ArrayList<Hotel> hotels = new ArrayList<>();
        for (int i = 1; i <= numberOfHotels; ++i) {
            hotels.add(new Hotel(i, new HotelName(hotelNames[i-1]),
                    new HotelDescription("To bardzo dobry hotel"),
                    new Address(i, new CityName(cities[i-1]),
                            new StreetName("Debowa"), new PostalCode("25-00" + i),
                            new BuildingNumber("5" + i))));
        }
        return hotels;
    }

    private List<HotelAmenity> createHotelAmenitiesForMock(int hotelId) {
        return List.of(
                new HotelAmenity(3, new HotelAmenityName("Wi-Fi"), hotelId),
                new HotelAmenity(17, new HotelAmenityName("Basen"), hotelId),
                new HotelAmenity(21, new HotelAmenityName("Bieżnia"), hotelId)
        );
    }

    private List<HotelRoom> createHotelRoomsForMock(int hotelId) {
        return List.of(
                new HotelRoom(3, new RoomGuestCapacity(3), hotelId),
                new HotelRoom(5, new RoomGuestCapacity(2), hotelId),
                new HotelRoom(12, new RoomGuestCapacity(5), hotelId),
                new HotelRoom(55, new RoomGuestCapacity(3), hotelId)
        );
    }

}