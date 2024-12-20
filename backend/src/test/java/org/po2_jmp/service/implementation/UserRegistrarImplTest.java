package org.po2_jmp.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.po2_jmp.entity.User;
import org.po2_jmp.repository.contract.UsersRepository;
import org.po2_jmp.request.UserRegistrationRequest;
import org.po2_jmp.response.ResponseStatus;
import org.po2_jmp.response.UserRegistrationResponse;
import org.po2_jmp.service.helper.UserRegistrationRequestValidator;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserRegistrarImplTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private UserRegistrationRequestValidator requestValidator;

    @InjectMocks
    private UserRegistrarImpl userRegistrar;

    @Test
    void Register_ShouldReturnResponseOfStatusBadRequest_WhenRequestIsInvalid() {
        UserRegistrationRequest request = createRequest();
        when(requestValidator.validate(any(UserRegistrationRequest.class)))
                .thenReturn(List.of("error"));

        UserRegistrationResponse response = userRegistrar.register(request);

        assertEquals(ResponseStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void Register_ShouldReturnResponseOfStatusInternalServerError_WhenUserWasNotSavedSuccessfully() {
        UserRegistrationRequest request = createRequest();
        when(requestValidator.validate(any(UserRegistrationRequest.class)))
                .thenReturn(List.of());
        when(usersRepository.add(any(User.class)))
                .thenReturn(Optional.empty());

        UserRegistrationResponse response = userRegistrar.register(request);

        assertEquals(ResponseStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    }

    @Test
    void Register_ShouldReturnResponseOfStatusCreated_WhenUserWasSavedSuccessfully() {
        UserRegistrationRequest request = createRequest();
        when(requestValidator.validate(any(UserRegistrationRequest.class)))
                .thenReturn(List.of());
        when(usersRepository.add(any(User.class)))
                .thenReturn(Optional.of(request.getUserId()));

        UserRegistrationResponse response = userRegistrar.register(request);

        assertEquals(ResponseStatus.CREATED, response.getStatus());
    }

    private UserRegistrationRequest createRequest() {
        return new UserRegistrationRequest("POST", "/register",
                "newUser", "password", "password");
    }

}