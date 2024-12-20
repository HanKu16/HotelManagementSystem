package org.po2_jmp.service.helper;

import org.po2_jmp.domain.UserLogin;
import org.po2_jmp.domain.UserPassword;
import org.po2_jmp.repository.contract.UsersRepository;
import org.po2_jmp.request.UserRegistrationRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserRegistrationRequestValidator {

    private final UsersRepository usersRepository;

    public UserRegistrationRequestValidator(UsersRepository usersRepository) {
        if (usersRepository == null) {
            throw new IllegalArgumentException("UsersRepository can " +
                    "not be null, but null was passed to " +
                    "UserRegistrationRequestValidator constructor");
        }
        this.usersRepository = usersRepository;
    }

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
            new UserLogin(userId);
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

