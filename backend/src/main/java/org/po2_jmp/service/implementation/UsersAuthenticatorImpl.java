package org.po2_jmp.service.implementation;

import org.po2_jmp.entity.Role;
import org.po2_jmp.entity.User;
import org.po2_jmp.repository.contract.RolesRepository;
import org.po2_jmp.repository.contract.UsersRepository;
import org.po2_jmp.request.UserAuthenticationRequest;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.response.UserAuthenticationResponse;
import org.po2_jmp.service.contract.UsersAuthenticator;
import java.util.Optional;

public class UsersAuthenticatorImpl implements UsersAuthenticator {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    public UsersAuthenticatorImpl(UsersRepository usersRepository,
            RolesRepository rolesRepository) {
        if (usersRepository == null) {
            throw new IllegalArgumentException("UsersRepository can not be null, " +
                    "but null was passed to UserAuthenticatorImpl");
        }
        if (rolesRepository == null) {
            throw new IllegalArgumentException("RolesRepository can not be null, " +
                    "but null was passed to UserAuthenticatorImpl");
        }
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public UserAuthenticationResponse authenticate(UserAuthenticationRequest request) {
        Optional<UserAuthenticationResponse> optionalResponse = validateRequest(request);
        if (optionalResponse.isPresent()) {
            return optionalResponse.get();
        }
        Optional<User> optionalUser = fetchUser(request);
        if (optionalUser.isEmpty()) {
            return new UserAuthenticationResponse(ResponseStatus.NOT_FOUND,
                    "invalid credentials");
        }
        User user = optionalUser.get();
        if (!verifyPassword(user, request)) {
            return new UserAuthenticationResponse(
                    ResponseStatus.UNAUTHORIZED, "invalid credentials");
        }
        Role role = fetchRole(user.getRoleId()).get();
        return buildSuccessResponse(user, role);
    }

    private Optional<UserAuthenticationResponse> validateRequest(
            UserAuthenticationRequest request) {
        if ((request.getUserId() == null) || (request.getPassword() == null)) {
            return Optional.of(new UserAuthenticationResponse(
                    ResponseStatus.BAD_REQUEST, "invalid credentials"));
        }
        return Optional.empty();
    }

    private Optional<User> fetchUser(UserAuthenticationRequest request) {
        return usersRepository.findById(request.getUserId());
    }

    private Optional<Role> fetchRole(int roleId) {
        return rolesRepository.findById(roleId);
    }

    private boolean verifyPassword(User user, UserAuthenticationRequest request) {
        return user.getPassword().getValue().equals(request.getPassword());
    }

    private UserAuthenticationResponse buildSuccessResponse(User user, Role role) {
        return new UserAuthenticationResponse(ResponseStatus.OK,
                "user was successfully authenticated",
                user.getId().getValue(),
                role.getName());
    }

}
