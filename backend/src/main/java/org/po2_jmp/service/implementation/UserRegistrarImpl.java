package org.po2_jmp.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.po2_jmp.domain.UserId;
import org.po2_jmp.domain.UserPassword;
import org.po2_jmp.entity.User;
import org.po2_jmp.repository.contract.UsersRepository;
import org.po2_jmp.request.UserRegistrationRequest;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.response.UserRegistrationResponse;
import org.po2_jmp.service.contract.UserRegistrar;
import org.po2_jmp.service.helper.UserRegistrationRequestValidator;

/**
 * The {@code UserRegistrarImpl} class implements the {@link UserRegistrar} interface.
 * It handles the user registration process by validating the registration request,
 * creating a new user, and saving it to the repository.
 */
public class UserRegistrarImpl implements UserRegistrar {

    private static final int USER_ROLE_ID = 1;
    private final UsersRepository usersRepository;
    private final UserRegistrationRequestValidator requestValidator;

    /**
     * Constructs a new {@code UserRegistrarImpl} instance with the specified repositories and validator.
     *
     * @param usersRepository The repository used for saving user data.
     * @param requestValidator The validator used for validating the user registration request.
     */
    public UserRegistrarImpl(UsersRepository usersRepository,
            UserRegistrationRequestValidator requestValidator) {
        if (usersRepository == null) {
            throw new IllegalArgumentException("UsersRepository can not be null," +
                    " but null was passed to UserRegistrarImpl");
        }
        if (requestValidator == null) {
            throw new IllegalArgumentException("UsersRegistrationRequestValidator " +
                    "can not be null, but null was passed to UserRegistrarImpl");
        }
        this.usersRepository = usersRepository;
        this.requestValidator = requestValidator;
    }

    /**
     * Registers a new user by validating the provided registration request, creating the user, and saving
     * the user to the repository.
     *
     * @param request The {@link UserRegistrationRequest} containing the user details for registration.
     * @return A {@link UserRegistrationResponse} indicating the result of the registration process.
     */
    @Override
    public UserRegistrationResponse register(UserRegistrationRequest request) {
        List<String> errors = requestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new UserRegistrationResponse(ResponseStatus.BAD_REQUEST,
                    String.join(", ", errors));
        }
        User user = createUser(request);
        Optional<String> optionalUserId = usersRepository.add(user);
        if (optionalUserId.isEmpty()) {
            return new UserRegistrationResponse(ResponseStatus.INTERNAL_SERVER_ERROR,
                    "failed to create user, please try again");
        }
        return new UserRegistrationResponse(ResponseStatus.CREATED,
                "user account was created successfully", optionalUserId.get());
    }

    private User createUser(UserRegistrationRequest request) {
        return new User(
                new UserId(request.getUserId()),
                new UserPassword(request.getPassword()),
                LocalDateTime.now(),
                USER_ROLE_ID
        );
    }

}
