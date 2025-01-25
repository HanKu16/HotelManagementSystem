package org.po2_jmp.controller.helper.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.po2_jmp.controller.helper.contract.CommandExtractor;
import org.po2_jmp.controller.helper.contract.ValidRequestResponder;
import org.po2_jmp.request.*;
import org.po2_jmp.response.*;
import org.po2_jmp.service.contract.*;
import java.util.Optional;

public class ValidRequestResponderImpl implements ValidRequestResponder {

    private final UsersAuthenticator usersAuthenticator;
    private final UserRegistrar userRegistrar;
    private final ReservationsCreator reservationsCreator;
    private final HotelsProvider hotelsProvider;
    private final ReservationsProvider reservationsProvider;
    private final ReservationsCanceler reservationsCanceler;
    private final CommandExtractor commandExtractor;
    private final JsonConverter jsonConverter;

    public ValidRequestResponderImpl(
            UsersAuthenticator usersAuthenticator,
            UserRegistrar userRegistrar,
            ReservationsCreator reservationsCreator,
            HotelsProvider hotelsProvider,
            ReservationsProvider reservationsProvider,
            ReservationsCanceler reservationsCanceler,
            CommandExtractor commandExtractor,
            JsonConverter jsonConverter) {
        validateParams(usersAuthenticator, userRegistrar,
                reservationsCreator, hotelsProvider,
                reservationsProvider, reservationsCanceler,
                commandExtractor, jsonConverter);
        this.usersAuthenticator = usersAuthenticator;
        this.userRegistrar = userRegistrar;
        this.reservationsCreator = reservationsCreator;
        this.hotelsProvider = hotelsProvider;
        this.reservationsProvider = reservationsProvider;
        this.reservationsCanceler = reservationsCanceler;
        this.commandExtractor = commandExtractor;
        this.jsonConverter = jsonConverter;
    }

    public String respond(String request) {
        Optional<String> optionalCommand = commandExtractor.extract(request);
        return optionalCommand.isPresent() ?
                handleRequestOfValidFormat(optionalCommand.get(), request) :
                handleNoCommandRequest();
    }

    private String handleRequestOfValidFormat(String command, String request) {
        String response;
        try {
            response = tryHandleRequestOfValidFormat(command, request);
        } catch (JsonProcessingException e) {
            response = handleJsonProcessingException();
        }
        return response;
    }

    private String tryHandleRequestOfValidFormat(String command, String message)
            throws JsonProcessingException {
        return switch (command) {
            case "authenticate" -> handleAuthenticationRequest(message);
            case "register" -> handleRegistrationRequest(message);
            case "createReservation" -> handleCreateReservationRequest(message);
            case "getHotelProfile" -> handleHotelProfileRequest(message);
            case "getHotelsOverviews" -> handleHotelsOverviewsRequest(message);
            case "getUserReservations" -> handleUserReservationsRequest(message);
            case "cancelReservation" -> handleCancelReservationRequest(message);
            default -> handleUnknownCommandRequest();
        };
    }

    private String handleAuthenticationRequest(String message)
            throws JsonProcessingException {
        UserAuthenticationRequest request = jsonConverter.deserialize(
                message, UserAuthenticationRequest.class);
        UserAuthenticationResponse response = usersAuthenticator.authenticate(request);
        return jsonConverter.serialize(response);
    }

    private String handleRegistrationRequest(String message)
            throws JsonProcessingException {
        UserRegistrationRequest request = jsonConverter.deserialize(
                message, UserRegistrationRequest.class);
        UserRegistrationResponse response = userRegistrar.register(request);
        return jsonConverter.serialize(response);
    }

    private String handleCreateReservationRequest(String message)
            throws JsonProcessingException {
        ReservationCreationRequest request = jsonConverter.deserialize(
                message, ReservationCreationRequest.class);
        ReservationCreationResponse response = reservationsCreator.create(request);
        return jsonConverter.serialize(response);
    }

    private String handleHotelProfileRequest(String message)
            throws JsonProcessingException {
        HotelProfileRequest request = jsonConverter.deserialize(
                message, HotelProfileRequest.class);
        HotelProfileResponse response = hotelsProvider.getProfile(request);
        return jsonConverter.serialize(response);
    }

    private String handleHotelsOverviewsRequest(String message)
            throws JsonProcessingException {
        HotelsOverviewsRequest request = jsonConverter.deserialize(
                message, HotelsOverviewsRequest.class);
        HotelsOverviewsResponse response = hotelsProvider
                .getHotelsOverviews(request);
        return jsonConverter.serialize(response);
    }

    private String handleUserReservationsRequest(String message)
            throws JsonProcessingException{
        UserReservationsRequest request = jsonConverter
                .deserialize(message, UserReservationsRequest.class);
        UserReservationsResponse response = reservationsProvider
                .getUserReservations(request);
        return jsonConverter.serialize(response);
    }

    private String handleCancelReservationRequest(String message)
            throws JsonProcessingException {
        ReservationCancellationRequest request = jsonConverter
                .deserialize(message, ReservationCancellationRequest.class);
        ReservationCancellationResponse response = reservationsCanceler.cancel(request);
        return jsonConverter.serialize(response);
    }

    private String handleUnknownCommandRequest() throws JsonProcessingException {
        Response invalidRequestResponse = new Response(
                ResponseStatus.METHOD_NOT_ALLOWED, "unknown command");
        return jsonConverter.serialize(invalidRequestResponse);
    }

    private String handleNoCommandRequest() {
        String response;
        try {
            Response invalidRequestResponse = new Response(
                    ResponseStatus.BAD_REQUEST,
                    "there was no command in the request");
            response = jsonConverter.serialize(invalidRequestResponse);
        } catch (JsonProcessingException e) {
            response = "{\"status\": \"BAD_REQUEST\", " +
                    "\"message\": \"there was no command in the request\"}";
        }
        return response;
    }

    private String handleJsonProcessingException() {
        String response;
        try {
            Response invalidRequestResponse = new Response(
                    ResponseStatus.INTERNAL_SERVER_ERROR,
                    "there was issue with json converting");
            response = jsonConverter.serialize(invalidRequestResponse);
        } catch (JsonProcessingException e) {
            response = "{\"status\": \"INTERNAL_SERVER_ERROR\", " +
                    "\"message\": \"there was issue with json converting\"}";
        }
        return response;
    }

    private void validateParams(UsersAuthenticator usersAuthenticator,
            UserRegistrar userRegistrar, ReservationsCreator reservationsCreator,
            HotelsProvider hotelsProvider, ReservationsProvider reservationsProvider,
            ReservationsCanceler reservationsCanceler, CommandExtractor commandExtractor,
            JsonConverter jsonConverter) {
        if (usersAuthenticator == null) {
            throw new IllegalArgumentException("UsersAuthenticator can not be null," +
                    " but null was passed to ValidRequestResponderImpl");
        }
        if (userRegistrar == null) {
            throw new IllegalArgumentException("UserRegistrar can not be null," +
                    " but null was passed to ValidRequestResponderImpl");
        }
        if (reservationsCreator == null) {
            throw new IllegalArgumentException("ReservationsCreator can not be null," +
                    " but null was passed to ValidRequestResponderImpl");
        }
        if (hotelsProvider == null) {
            throw new IllegalArgumentException("HotelsProvider can not be null," +
                    " but null was passed to ValidRequestResponderImpl");
        }
        if (reservationsProvider == null) {
            throw new IllegalArgumentException("ReservationsProvider can not be null," +
                    " but null was passed to ValidRequestResponderImpl");
        }
        if (reservationsCanceler == null) {
            throw new IllegalArgumentException("ReservationsCanceler can not be null," +
                    " but null was passed to ValidRequestResponderImpl");
        }
        if (commandExtractor == null) {
            throw new IllegalArgumentException("CommandExtractor can not be null," +
                    " but null was passed to ValidRequestResponderImpl");
        }
        if (jsonConverter == null) {
            throw new IllegalArgumentException("JsonConverter can not be null," +
                    " but null was passed to ValidRequestResponderImpl");
        }
    }

}
