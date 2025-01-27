package org.po2_jmp.controller.helper.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.po2_jmp.Main;
import org.po2_jmp.controller.helper.RequestValidationResult;
import org.po2_jmp.controller.helper.contract.CommandExtractor;
import org.po2_jmp.controller.helper.contract.InvalidRequestResponder;
import org.po2_jmp.controller.helper.contract.RequestValidator;
import org.po2_jmp.controller.helper.contract.ValidRequestResponder;
import org.po2_jmp.repository.contract.*;
import org.po2_jmp.repository.helper.DbUtils;
import org.po2_jmp.repository.helper.DbUtilsImpl;
import org.po2_jmp.repository.implementation.*;
import org.po2_jmp.service.contract.*;
import org.po2_jmp.service.helper.*;
import org.po2_jmp.service.implementation.*;
import java.io.InputStream;
import java.util.Properties;
import static java.lang.System.err;

/**
 * Redirects processing and responding to messages based on request validation.
 * <p>
 * The {@link MessageResponder} class is responsible for validating incoming
 * requests and generating appropriate responses based on whether the request
 * is valid or not. It integrates multiple services and repositories to process
 * valid requests and generate responses accordingly.
 * </p>
 */
public class MessageResponder {

    private static final Logger LOGGER = LogManager.getLogger(MessageResponder.class);
    private final RequestValidator validator;
    private final InvalidRequestResponder invalidRequestResponder;
    private final ValidRequestResponder validRequestResponder;

    /**
     * Constructs a {@link MessageResponder} with default repositories, services,
     * and responders.
     * <p>
     * This constructor initializes the necessary repositories and services, such as:
     * <ul>
     *     <li>Database repositories for users, roles, reservations, hotels, etc.</li>
     *     <li>Various validators like {@link UserRegistrationRequestValidator} and
     *     {@link ReservationCreationRequestValidator}.</li>
     *     <li>Request extractor and responder implementations.</li>
     * </ul>
     * </p>
     */
    public MessageResponder() {
        String url = "";
        String user = "";
        String password = "";

        Properties properties = new Properties();
        try (InputStream inputStream = Main.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                LOGGER.info("Can not read database properties from " +
                                "config.properties because file does not " +
                                "exist, app will be closed: {}",
                        err.toString());
                System.exit(1);
            }
            properties.load(inputStream);
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
        } catch (Exception e) {
            LOGGER.info("Error happen during initialization in MessageResponder" +
                    " app will be closed");
            System.exit(1);
        }

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

    /**
     * Constructs a {@link MessageResponder}.
     * <p>
     * Should be used only for tests purpose.
     * </p>
     *
     * @param requestValidator the {@link RequestValidator} to validate requests
     * @param invalidRequestResponder the {@link InvalidRequestResponder} to respond to invalid requests
     * @param validRequestResponder the {@link ValidRequestResponder} to respond to valid requests
     */
    public MessageResponder(RequestValidator requestValidator,
            InvalidRequestResponder invalidRequestResponder,
            ValidRequestResponder validRequestResponder) {
        this.validator = requestValidator;
        this.invalidRequestResponder = invalidRequestResponder;
        this.validRequestResponder = validRequestResponder;
    }

    /**
     * Responds to the given message by validating it and generating an appropriate response.
     * <p>
     * The method first validates the request using the {@link RequestValidator}.
     * If the request is valid, it generates a response using the
     * {@link ValidRequestResponder}. If the request is invalid,
     * it generates a response using the {@link InvalidRequestResponder}.
     * </p>
     *
     * @param message the request message to respond to
     * @return the generated response as a {@link String}
     */
    public String respond(String message) {
        RequestValidationResult validationResult = validator.validate(message);
        return (validationResult == RequestValidationResult.OK) ?
                validRequestResponder.respond(message) :
                invalidRequestResponder.respond(validationResult);
    }

}
