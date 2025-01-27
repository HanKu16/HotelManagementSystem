package org.po2_jmp.service.helper;

import org.po2_jmp.domain.UserId;
import org.po2_jmp.domain.UserPassword;
import org.po2_jmp.repository.contract.UsersRepository;
import org.po2_jmp.request.UserRegistrationRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The {@code UserRegistrationRequestValidator} class is responsible for validating a user
 * registration request. It checks the validity of the user ID and password, ensuring that
 * the user ID is correctly formatted, does not already exist, and that the password meets
 * the specified criteria and matches the confirmed password.
 */
public class UserRegistrationRequestValidator {

    private final UsersRepository usersRepository;

    /**
     * Constructs a new {@code UserRegistrationRequestValidator} with the specified
     * {@link UsersRepository}.
     *
     * @param usersRepository The {@link UsersRepository} used to check if the user ID already exists.
     * @throws IllegalArgumentException if the {@link UsersRepository} is {@code null}.
     */
    public UserRegistrationRequestValidator(UsersRepository usersRepository) {
        if (usersRepository == null) {
            throw new IllegalArgumentException("UsersRepository can " +
                    "not be null, but null was passed to " +
                    "UserRegistrationRequestValidator constructor");
        }
        this.usersRepository = usersRepository;
    }

    /**
     * Validates the given {@link UserRegistrationRequest} by checking the user ID and password.
     * It will return a list of error messages, which will be empty if the request is valid.
     *
     * @param request The {@link UserRegistrationRequest} to be validated.
     * @return A list of error messages. If the request is valid, the list will be empty.
     */
    public List<String> validate(UserRegistrationRequest request) {
        List<String> userIdErrors = isUserIdValid(request.getUserId());
        List<String> passwordErrors = isPasswordValid(request.getPassword(),
                request.getConfirmedPassword());
        return Stream.concat(userIdErrors.stream(), passwordErrors.stream())
                .collect(Collectors.toList());
    }

    private List<String> isUserIdValid(String userId) {
        List<String> errors = new ArrayList<>();
        try {
            new UserId(userId);
        } catch (IllegalArgumentException e) {
            errors.add(e.getMessage());
        }
        if (usersRepository.findById(userId).isPresent()) {
            errors.add("user with the given login already exists");
        }
        return errors;
    }

    private List<String> isPasswordValid(String password, String confirmedPassword) {
        List<String> errors = new ArrayList<>();
        try {
            new UserPassword(password);
        } catch (IllegalArgumentException e) {
            errors.add(e.getMessage());
        }
        if (!password.equals(confirmedPassword)) {;
            errors.add("passwords do not match");
        }
        return errors;
    }

}

