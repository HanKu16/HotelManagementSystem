package org.po2_jmp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.po2_jmp.repository.contract.*;
import org.po2_jmp.repository.helper.DbUtils;
import org.po2_jmp.repository.helper.DbUtilsImpl;
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
    private final ReservationsProvider reservationsProvider;
    private final ReservationsCanceler reservationsCanceler;
    private final JsonConverter jsonConverter;

    public MessageResponder() {
        String url = "jdbc:postgresql://localhost:5432/hotel_management_system_db";
        String user = "postgres";
        String password = "1234";

        DbUtils dbUtils = new DbUtilsImpl(url, user, password);
        UsersRepository usersRepository = new DbUsersRepository(dbUtils);
        RolesRepository rolesRepository = new DbRolesRepository(dbUtils);
        ReservationsRepository reservationsRepository =
                new DbReservationsRepository(dbUtils);
        HotelsRepository hotelsRepository = new DbHotelsRepository(dbUtils);
        HotelRoomsRepository hotelRoomsRepository =
                new DbHotelRoomsRepository(dbUtils);
        HotelAmenitiesRepository hotelAmenitiesRepository =
                new DbHotelAmenitiesRepository(dbUtils);

        UserRegistrationRequestValidator userRegistrationRequestValidator =
                new UserRegistrationRequestValidator(usersRepository);
        AvailableRoomFinder availableRoomFinder =
                new AvailableRoomFinder(reservationsRepository);
        ReservationCreationRequestValidator reservationCreationRequestValidator =
                new ReservationCreationRequestValidator(
                        usersRepository, hotelsRepository);

        this.jsonConverter = new JsonConverter();
        this.usersAuthenticator = new UsersAuthenticatorImpl(
                usersRepository, rolesRepository);
        this.userRegistrar = new UserRegistrarImpl(
                usersRepository, userRegistrationRequestValidator);
        this.reservationsCreator = new ReservationsCreatorImpl(
                reservationsRepository, hotelsRepository, hotelRoomsRepository,
                availableRoomFinder, reservationCreationRequestValidator);
        this.hotelsProvider = new HotelsProviderImpl(
                hotelsRepository, hotelAmenitiesRepository,
                hotelRoomsRepository);
        this.reservationsProvider = new ReservationsProviderImpl(
                reservationsRepository, hotelRoomsRepository, hotelsRepository);
        this.reservationsCanceler = new ReservationsCancelerImpl(reservationsRepository);
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
