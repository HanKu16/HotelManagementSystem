package org.po2_jmp.service.contract;

import org.po2_jmp.request.UserAuthenticationRequest;
import org.po2_jmp.response.UserAuthenticationResponse;

/**
 * The {@code UsersAuthenticator} interface defines the contract for authenticating a user.
 * Implementations of this interface are responsible for validating the user's credentials and
 * providing an appropriate authentication response.
 */
public interface UsersAuthenticator {

    /**
     * Authenticates a user based on the provided request data.
     *
     * @param request The {@link UserAuthenticationRequest} containing the
     *        user's authentication details.
     * @return A {@link UserAuthenticationResponse} containing the result
     *         of the authentication process.
     */
    UserAuthenticationResponse authenticate(UserAuthenticationRequest request);

}
