package org.po2_jmp.service.helper;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.po2_jmp.repository.contract.UsersRepository;
import org.po2_jmp.request.UserRegistrationRequest;

@ExtendWith(MockitoExtension.class)
class UserRegistrationRequestValidatorTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UserRegistrationRequestValidator requestValidator;

    private final String requestCommand = "register";

    @Test
    void Validate_ShouldReturnEmptyList_WhenRequestIsValid() {
        String userId = "validUser";
        String password = "validPassword123";
        String confirmedPassword = "validPassword123";
        UserRegistrationRequest request = new UserRegistrationRequest(
                requestCommand, userId, password, confirmedPassword);
        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        List<String> errors = requestValidator.validate(request);

        assertTrue(errors.isEmpty());
    }

    @Test
    void Validate_ShouldReturnListOfSize1_WhenUserIdIsTooLong() {
        String userId = "userIdThatIsTooLong15";
        String password = "validPassword123";
        String confirmedPassword = "validPassword123";
        UserRegistrationRequest request = new UserRegistrationRequest(
                requestCommand, userId, password, confirmedPassword);
        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        List<String> errors = requestValidator.validate(request);

        assertEquals(1, errors.size());
    }

    @Test
    void Validate_ShouldReturnListOfSize1_WhenPasswordsAreNotTheSame() {
        String userId = "validUser1";
        String password = "Password1";
        String confirmedPassword = "password2";
        UserRegistrationRequest request = new UserRegistrationRequest(
                requestCommand, userId, password, confirmedPassword);
        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        List<String> errors = requestValidator.validate(request);

        assertEquals(1, errors.size());
    }

    @Test
    void Validate_ShouldReturnListOfSize2_WhenUserIdIsInvalidAndPasswordIsInvalid() {
        String userId = "invalidUser1*";
        String password = "password/1";
        String confirmedPassword = "password/1";
        UserRegistrationRequest request = new UserRegistrationRequest(
                requestCommand, userId, password, confirmedPassword);
        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        List<String> errors = requestValidator.validate(request);

        assertEquals(2, errors.size());
    }

}