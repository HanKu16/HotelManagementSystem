package org.po2_jmp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.po2_jmp.repository.contract.*;
import org.po2_jmp.repository.implementation.*;
import org.po2_jmp.request.*;
import org.po2_jmp.response.*;
import org.po2_jmp.service.contract.*;
import org.po2_jmp.service.helper.*;
import org.po2_jmp.service.implementation.*;
import java.util.Optional;

public class MessageResponder {

    private final UsersAuthenticator usersAuthenticator;
    private final UserRegistrar userRegistrar;
    private final ReservationsCreator reservationsCreator;
    private final HotelsProvider hotelsProvider;
    private final JsonConverter jsonConverter;

    public MessageResponder() {
        String url = "jdbc:postgresql://localhost:5432/hotel_management_system_db";
        String user = "postgres";
        String password = "1234";

        UsersRepository usersRepository = new DbUsersRepository(url, user, password);
        RolesRepository rolesRepository = new DbRolesRepository(url, user, password);
        ReservationsRepository reservationsRepository = new DbReservationsRepository(
                url, user, password);
        HotelsRepository hotelsRepository = new DbHotelsRepository(url, user, password);
        HotelRoomsRepository hotelRoomsRepository = new DbHotelRoomsRepository(
                url, user, password);
        HotelAmenitiesRepository hotelAmenitiesRepository =
                new DbHotelAmenitiesRepository(url, user, password);

        UserRegistrationRequestValidator userRegistrationRequestValidator =
                new UserRegistrationRequestValidator(usersRepository);
        AvailableRoomFinder availableRoomFinder = new AvailableRoomFinder(reservationsRepository);
        ReservationCreationRequestValidator reservationCreationRequestValidator =
                new ReservationCreationRequestValidator(usersRepository, hotelsRepository);
        HotelsProvider hotelsProvider = new HotelsProviderImpl(
                hotelsRepository, hotelAmenitiesRepository);

        this.jsonConverter = new JsonConverter();
        this.usersAuthenticator = new UsersAuthenticatorImpl(
                usersRepository, rolesRepository);
        this.userRegistrar = new UserRegistrarImpl(
                usersRepository, userRegistrationRequestValidator);
        this.reservationsCreator = new ReservationsCreatorImpl(
                reservationsRepository, hotelsRepository, hotelRoomsRepository,
                availableRoomFinder, reservationCreationRequestValidator);
        this.hotelsProvider = hotelsProvider;
    }

    public String respond(String message) {
        if (message == null) {
            return handleBadRequestFormat();
        }
        Optional<JSONObject> optionalJSONObject = convertMessageToJsonObject(message);
        if (optionalJSONObject.isEmpty()) {
            return handleBadRequestFormat();
        }
        Optional<String> optionalCommand = getCommand(optionalJSONObject.get());
        if (optionalCommand.isEmpty()) {
            return handleRequestWithoutCommand();
        }
        return handleRequestOfValidFormat(optionalCommand.get(), message);
    }

    private String handleRequestOfValidFormat(String command, String message) {
        String response;
        try {
            response = tryHandleRequestOfValidFormat(command, message);
        } catch (JsonProcessingException e) {
            response = handleJsonConvertingError();
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
            default -> handleUnknownCommandRequest();
        };
    }

    private String handleAuthenticationRequest(String message)
            throws JsonProcessingException {
        UserAuthenticationRequest authenticationRequest = jsonConverter.deserialize(
                message, UserAuthenticationRequest.class);
        UserAuthenticationResponse authenticationResponse =
                usersAuthenticator.authenticate(authenticationRequest);
        return jsonConverter.serialize(authenticationResponse);
    }

    private String handleRegistrationRequest(String message)
            throws JsonProcessingException {
        UserRegistrationRequest registrationRequest = jsonConverter.deserialize(
                message, UserRegistrationRequest.class);
        UserRegistrationResponse registrationResponse = userRegistrar.register(
                registrationRequest);
        return jsonConverter.serialize(registrationResponse);
    }

    private String handleCreateReservationRequest(String message)
            throws JsonProcessingException {
        ReservationCreationRequest reservationCreationRequest = jsonConverter
                .deserialize(message, ReservationCreationRequest.class);
        ReservationCreationResponse reservationCreationResponse =
                reservationsCreator.create(reservationCreationRequest);
        return jsonConverter.serialize(reservationCreationResponse);
    }

    private String handleHotelProfileRequest(String message)
            throws JsonProcessingException {
        HotelProfileRequest hotelProfileRequest = jsonConverter
                .deserialize(message, HotelProfileRequest.class);
        HotelProfileResponse hotelProfileResponse = hotelsProvider
                .getProfileById(hotelProfileRequest);
        return jsonConverter.serialize(hotelProfileResponse);
    }

    private String handleHotelsOverviewsRequest(String message)
            throws JsonProcessingException {
        HotelsOverviewsRequest hotelsOverviewsRequest = jsonConverter
                .deserialize(message, HotelsOverviewsRequest.class);
        HotelsOverviewsResponse hotelsOverviewsResponse = hotelsProvider
                .getHotelsOverviews(hotelsOverviewsRequest);
        return jsonConverter.serialize(hotelsOverviewsResponse);
    }

    private String handleUnknownCommandRequest() throws JsonProcessingException {
        Response invalidRequestResponse = new Response(
                ResponseStatus.METHOD_NOT_ALLOWED, "unknown command");
        return jsonConverter.serialize(invalidRequestResponse);
    }

    private String handleBadRequestFormat() {
        String response;
        try {
            Response invalidRequestResponse = new Response(
                    ResponseStatus.BAD_REQUEST, "invalid request format");
            response = jsonConverter.serialize(invalidRequestResponse);
        } catch (JsonProcessingException e) {
            response = "{\"status\": \"BAD_REQUEST\", " +
                    "\"message\": \"invalid request format\"}";
        }
        return response;
    }

    private String handleJsonConvertingError() {
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

    private String handleRequestWithoutCommand() {
        String response;
        try {
            Response invalidRequestResponse = new Response(
                    ResponseStatus.METHOD_NOT_ALLOWED,
                    "command was not specified in request");
            response = jsonConverter.serialize(invalidRequestResponse);
        } catch (JsonProcessingException e) {
            response = "{\"status\": \"METHOD_NOT_ALLOWED\", " +
                    "\"message\": \"command was not specified in request\"}";
        }
        return response;
    }

    private Optional<JSONObject> convertMessageToJsonObject(String message) {
        Optional<JSONObject> optionalJsonObject;
        try {
            JSONObject jsonObject = new JSONObject(message);
            optionalJsonObject = Optional.of(jsonObject);
        } catch (JSONException e) {
            optionalJsonObject = Optional.empty();
        }
        return optionalJsonObject;
    }

    private Optional<String> getCommand(JSONObject jsonMessage) {
        Optional<String> optionalCommand;
        try {
            String command = jsonMessage.getString("command");
            optionalCommand = Optional.of(command);
        } catch (JSONException e) {
            optionalCommand = Optional.empty();
        }
        return optionalCommand;
    }

}
