package org.po2_jmp.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.po2_jmp.domain.UserId;
import org.po2_jmp.entity.Reservation;
import org.po2_jmp.repository.contract.ReservationsRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.po2_jmp.request.ReservationCancellationRequest;
import org.po2_jmp.response.ReservationCancellationResponse;
import org.po2_jmp.response.ResponseStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReservationsCancelerImplTest {

    @Mock
    private ReservationsRepository reservationsRepository;

    @InjectMocks
    private ReservationsCancelerImpl reservationsCanceler;

    @BeforeEach
    void setUp() {
        reservationsCanceler = new ReservationsCancelerImpl(reservationsRepository);
    }

    @ParameterizedTest
    @ValueSource(ints = { 5, 16, 91})
    void Cancel_ShouldReturnResponseOfStatusNotFound_WhenReservationOfGivenIdDoesNotExist(int reservationId) {
        when(reservationsRepository.findById(reservationId)).thenReturn(Optional.empty());

        ReservationCancellationRequest request = new ReservationCancellationRequest(
                "cancelReservation", reservationId);
        ReservationCancellationResponse response = reservationsCanceler.cancel(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    void Cancel_ShouldReturnStatusOfBadRequest_WhenReservationDateIsInThePast() {
        int reservationId = 5;
        LocalDateTime creationDateTime = LocalDateTime.of(2023, 12, 20, 10, 30);;
        LocalDate reservationDate = LocalDate.of(2023, 12, 25);
        Reservation reservation = new Reservation(reservationId, reservationDate,
                creationDateTime, new UserId("pawel123"), 5);
        when(reservationsRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        ReservationCancellationRequest request = new ReservationCancellationRequest("cancelReservation", reservationId);
        ReservationCancellationResponse response = reservationsCanceler.cancel(request);

        assertEquals(ResponseStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void Cancel_ShouldReturnResponseOfStatusOfOk_WhenReservationWasDeleted() {
        int reservationId = 5;
        LocalDateTime creationDateTime = LocalDateTime.of(2023, 12, 20, 10, 30);;
        LocalDate reservationDate = LocalDate.of(3000, 12, 25);
        Reservation reservation = new Reservation(reservationId, reservationDate,
                creationDateTime, new UserId("pawel123"), 5);
        when(reservationsRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationsRepository.deleteById(reservationId)).thenReturn(1);

        ReservationCancellationRequest request = new ReservationCancellationRequest("cancelReservation", reservationId);
        ReservationCancellationResponse response = reservationsCanceler.cancel(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    void Cancel_ShouldReturnResponseOfStatusNotFound_WhenReservationWasNotDeleted() {
        int reservationId = 5;
        LocalDateTime creationDateTime = LocalDateTime.of(2023, 12, 20, 10, 30);;
        LocalDate reservationDate = LocalDate.of(3000, 12, 25);
        Reservation reservation = new Reservation(reservationId, reservationDate,
                creationDateTime, new UserId("pawel123"), 5);
        when(reservationsRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationsRepository.deleteById(reservationId)).thenReturn(0);

        ReservationCancellationRequest request = new ReservationCancellationRequest("cancelReservation", reservationId);
        ReservationCancellationResponse response = reservationsCanceler.cancel(request);

        assertEquals(ResponseStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    }

}