package org.po2_jmp.controller.helper.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.po2_jmp.controller.helper.RequestValidationResult;
import org.po2_jmp.controller.helper.contract.InvalidRequestResponder;
import org.po2_jmp.controller.helper.contract.RequestValidator;
import org.po2_jmp.controller.helper.contract.ValidRequestResponder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageResponderTest {

    @Mock
    private RequestValidator validator;

    @Mock
    private InvalidRequestResponder invalidRequestResponder;

    @Mock
    private ValidRequestResponder validRequestResponder;

    @InjectMocks
    private MessageResponder messageResponder;

    @Test
    void Respond_ShouldCallValidRequestResponder_WhenValidationResultIsOK() {
        when(validator.validate(any())).thenReturn(RequestValidationResult.OK);

        String response = messageResponder.respond("");

        verify(validRequestResponder, times(1)).respond("");
        verify(invalidRequestResponder, never()).respond(any());
    }

    @Test
    void Respond_ShouldCallInvalidRequestResponder_WhenValidationResultIsBadFormat() {
        when(validator.validate(any())).thenReturn(RequestValidationResult.BAD_FORMAT);

        String response = messageResponder.respond("");

        verify(invalidRequestResponder, times(1)).respond(
                RequestValidationResult.BAD_FORMAT);
        verify(validRequestResponder, never()).respond(any());
    }

    @Test
    void Respond_ShouldCallInvalidRequestResponder_WhenValidationResultIsNull() {
        when(validator.validate(any())).thenReturn(RequestValidationResult.IS_NULL);

        String response = messageResponder.respond("");

        verify(invalidRequestResponder, times(1)).respond(
                RequestValidationResult.IS_NULL);
        verify(validRequestResponder, never()).respond(any());
    }

    @Test
    void Respond_ShouldCallInvalidRequestResponder_WhenValidationResultIsNoCommand() {
        when(validator.validate(any())).thenReturn(RequestValidationResult.NO_COMMAND);

        String response = messageResponder.respond("");

        verify(invalidRequestResponder, times(1)).respond(
                RequestValidationResult.NO_COMMAND);
        verify(validRequestResponder, never()).respond(any());
    }

}