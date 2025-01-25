package org.po2_jmp.controller.helper.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.po2_jmp.controller.helper.RequestValidationResult;
import org.po2_jmp.response.Response;
import org.po2_jmp.response.ResponseStatus;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvalidRequestResponderImplTest {

    @Mock
    private JsonConverter jsonConverter;

    @InjectMocks
    private InvalidRequestResponderImpl invalidRequestResponder;

    @Test
    void Respond_ShouldReturnBadRequest_WhenValidationResultIsBadFormat()
            throws JsonProcessingException {
        Response expectedResponse = new Response(ResponseStatus.BAD_REQUEST,
                "invalid request format");
        String expectedJsonResponse = "{\"status\": \"BAD_REQUEST\", " +
                "\"message\": \"invalid request format\"}";
        when(jsonConverter.serialize(expectedResponse)).thenReturn(expectedJsonResponse);

        String responseAsText = invalidRequestResponder.respond(
                RequestValidationResult.BAD_FORMAT);

        assertEquals(expectedJsonResponse, responseAsText);
    }

    @Test
    void Respond_ShouldReturnMethodNotAllowed_WhenValidationResultIsNoCommand() throws JsonProcessingException {
        Response expectedResponse = new Response(ResponseStatus.METHOD_NOT_ALLOWED,
                "command was not specified in request");
        String expectedJsonResponse = "{\"status\": \"METHOD_NOT_ALLOWED\", " +
                "\"message\": \"command was not specified in request\"}";

        when(jsonConverter.serialize(expectedResponse)).thenReturn(expectedJsonResponse);
        String responseAsText = invalidRequestResponder.respond(RequestValidationResult.NO_COMMAND);

        assertEquals(expectedJsonResponse, responseAsText);
    }

    @Test
    void Respond_ShouldThrowException_WhenValidationResultIsOk() {
        assertThrows(IllegalArgumentException.class,
                () -> invalidRequestResponder.respond(RequestValidationResult.OK));
    }

}