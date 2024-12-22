package org.po2_jmp.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.po2_jmp.domain.RoleName;
import org.po2_jmp.domain.UserId;
import org.po2_jmp.domain.UserPassword;
import org.po2_jmp.entity.Role;
import org.po2_jmp.entity.User;
import org.po2_jmp.repository.contract.RolesRepository;
import org.po2_jmp.repository.contract.UsersRepository;
import org.po2_jmp.request.UserAuthenticationRequest;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.response.UserAuthenticationResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersAuthenticatorImplTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private RolesRepository rolesRepository;

    @InjectMocks
    private UsersAuthenticatorImpl usersAuthenticator;

    @Test
    void Authenticate_ShouldReturnResponseOfStatusBadRequest_WhenUserIdIsInvalid() {
        UserAuthenticationRequest request = new UserAuthenticationRequest(
                "GET", "login", null, "ptaku213");

        UserAuthenticationResponse response = usersAuthenticator.authenticate(request);

        assertEquals(ResponseStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void Authenticate_ShouldReturnResponseOfStatusBadRequest_WhenPasswordIsInvalid() {
        UserAuthenticationRequest request = new UserAuthenticationRequest(
                "GET", "login", "szpaku", null);

        UserAuthenticationResponse response = usersAuthenticator.authenticate(request);

        assertEquals(ResponseStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void Authenticate_ShouldReturnResponseOfStatusNotFound_WhenUsersDoesNotExist() {
        String userId = "szpaku";
        UserAuthenticationRequest request = new UserAuthenticationRequest(
                "GET", "login", userId, "password123");
        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        UserAuthenticationResponse response = usersAuthenticator.authenticate(request);

        assertEquals(ResponseStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    void Authenticate_ShouldReturnResponseOfUnauthorized_WhenCredentialsAreInvalid() {
        String userId = "michal13";
        User user = new User(new UserId(userId), new UserPassword("pokemon1"),
                LocalDateTime.of(2017, 2, 3, 6, 30, 0, 0), 1);
        UserAuthenticationRequest request = new UserAuthenticationRequest(
                "GET", "login", userId, "pokemon513");
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        UserAuthenticationResponse response = usersAuthenticator.authenticate(request);

        assertEquals(ResponseStatus.UNAUTHORIZED, response.getStatus());
    }

    @Test
    void Authenticate_ShouldReturnResponseOfOk_WhenCredentialsAreValid() {
        String userId = "michal13";
        String password = "pokemon1";
        int roleId = 1;
        User user = new User(new UserId(userId), new UserPassword(password),
                LocalDateTime.of(2017, 2, 3, 6, 30, 0, 0), roleId);
        UserAuthenticationRequest request = new UserAuthenticationRequest(
                "GET", "login", userId, password);
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(rolesRepository.findById(roleId)).thenReturn(Optional.of(
                new Role(roleId, RoleName.USER)
        ));

        UserAuthenticationResponse response = usersAuthenticator.authenticate(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
    }

}