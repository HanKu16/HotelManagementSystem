package org.po2_jmp.controller.helper.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.po2_jmp.controller.helper.contract.CommandExtractor;
import org.po2_jmp.service.contract.*;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidRequestResponderImplTest {

    @Mock
    private UsersAuthenticator usersAuthenticator;

    @Mock
    private UserRegistrar userRegistrar;

    @Mock
    private ReservationsCreator reservationsCreator;

    @Mock
    private HotelsProvider hotelsProvider;

    @Mock
    private ReservationsProvider reservationsProvider;

    @Mock
    private ReservationsCanceler reservationsCanceler;

    @Mock
    private CommandExtractor commandExtractor;

    @Mock
    private JsonConverter jsonConverter;

    @InjectMocks
    private ValidRequestResponderImpl validRequestResponder;

    @Test
    void Respond_ShouldCallAuthenticateOnce_WhenCommandIsAuthenticate() {
        when(commandExtractor.extract(any())).thenReturn(Optional.of("authenticate"));

        validRequestResponder.respond("");

        verify(usersAuthenticator, times(1)).authenticate(any());
    }
    @Test
    void Respond_ShouldCallRegisterOnce_WhenCommandIsRegister() {
        when(commandExtractor.extract(any())).thenReturn(Optional.of("register"));

        validRequestResponder.respond("");

        verify(userRegistrar, times(1)).register(any());
    }

    @Test
    void Respond_ShouldCallCreateOnce_WhenCommandIsCancelReservation() {
        when(commandExtractor.extract(any())).thenReturn(Optional.of("createReservation"));

        validRequestResponder.respond("");

        verify(reservationsCreator, times(1)).create(any());
    }

    @Test
    void Respond_ShouldCallGetProfileOnce_WhenCommandIsGetHotelProfile() {
        when(commandExtractor.extract(any())).thenReturn(Optional.of("getHotelProfile"));

        validRequestResponder.respond("");

        verify(hotelsProvider, times(1)).getProfile(any());
    }

    @Test
    void Respond_ShouldCallGetHotelsOverviewsOnce_WhenCommandIsGetHotelsOverviews() {
        when(commandExtractor.extract(any())).thenReturn(Optional.of("getHotelsOverviews"));

        validRequestResponder.respond("");

        verify(hotelsProvider, times(1)).getHotelsOverviews(any());
    }

    @Test
    void Respond_ShouldCallGetUserReservationsOnce_WhenCommandIsGetUserReservations() {
        when(commandExtractor.extract(any())).thenReturn(Optional.of("getUserReservations"));

        validRequestResponder.respond("");

        verify(reservationsProvider, times(1)).getUserReservations(any());
    }

    @Test
    void Respond_ShouldCallCancelOnce_WhenCommandIsCancelReservation() {
        when(commandExtractor.extract(any())).thenReturn(Optional.of("cancelReservation"));

        validRequestResponder.respond("");

        verify(reservationsCanceler, times(1)).cancel(any());
    }

}
