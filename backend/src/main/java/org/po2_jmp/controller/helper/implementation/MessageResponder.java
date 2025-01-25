package org.po2_jmp.controller.helper.implementation;

import org.po2_jmp.controller.helper.RequestValidationResult;
import org.po2_jmp.controller.helper.contract.CommandExtractor;
import org.po2_jmp.controller.helper.contract.InvalidRequestResponder;
import org.po2_jmp.controller.helper.contract.ValidRequestResponder;
import org.po2_jmp.repository.contract.*;
import org.po2_jmp.repository.helper.DbUtils;
import org.po2_jmp.repository.helper.DbUtilsImpl;
import org.po2_jmp.repository.implementation.*;
import org.po2_jmp.service.contract.*;
import org.po2_jmp.service.helper.*;
import org.po2_jmp.service.implementation.*;

public class MessageResponder {

    private final JsonRequestValidator validator;
    private final InvalidRequestResponder invalidRequestResponder;
    private final ValidRequestResponder validRequestResponder;

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
        CommandExtractor commandExtractor = new JsonCommandExtractor();
        JsonConverter jsonConverter = new JsonConverter();

        UsersAuthenticator usersAuthenticator = new UsersAuthenticatorImpl(
                usersRepository, rolesRepository);
        UserRegistrar userRegistrar = new UserRegistrarImpl(
                usersRepository, userRegistrationRequestValidator);
        ReservationsCreator reservationsCreator = new ReservationsCreatorImpl(
                reservationsRepository, hotelsRepository, hotelRoomsRepository,
                availableRoomFinder, reservationCreationRequestValidator);
        HotelsProvider hotelsProvider = new HotelsProviderImpl(hotelsRepository,
                hotelAmenitiesRepository, hotelRoomsRepository);
        ReservationsProvider reservationsProvider = new ReservationsProviderImpl(
                reservationsRepository, hotelRoomsRepository, hotelsRepository);
        ReservationsCanceler reservationsCanceler =
                new ReservationsCancelerImpl(reservationsRepository);

        this.validator = new JsonRequestValidator(commandExtractor);
        this.invalidRequestResponder = new InvalidRequestResponderImpl(jsonConverter);
        this.validRequestResponder = new ValidRequestResponderImpl(
                usersAuthenticator, userRegistrar, reservationsCreator,
                hotelsProvider, reservationsProvider, reservationsCanceler,
                commandExtractor, jsonConverter);
    }

    public String respond(String message) {
        RequestValidationResult validationResult = validator.validate(message);
        return (validationResult == RequestValidationResult.OK) ?
                validRequestResponder.respond(message) :
                invalidRequestResponder.respond(validationResult);
    }

}
