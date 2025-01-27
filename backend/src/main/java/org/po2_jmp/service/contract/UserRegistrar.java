package org.po2_jmp.service.contract;

import org.po2_jmp.request.UserRegistrationRequest;
import org.po2_jmp.response.UserRegistrationResponse;

/**
 * The {@code UserRegistrar} interface defines the contract for registering a new user.
 * Implementations of this interface are responsible for processing the registration
 * of a new user based on the provided user registration request.
 */
public interface UserRegistrar {

    /**
     * Registers a new user based on the provided request data.
     *
     * @param request The {@link UserRegistrationRequest} containing the user
     *         details to be registered.
     * @return A {@link UserRegistrationResponse} containing the result of
     *         the user registration operation.
     */
    UserRegistrationResponse register(UserRegistrationRequest request);

}
