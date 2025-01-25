package org.po2_jmp.controller.helper.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.po2_jmp.controller.helper.RequestValidationResult;
import org.po2_jmp.controller.helper.contract.CommandExtractor;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonRequestValidatorTest {

    @Mock
    private CommandExtractor commandExtractor;

    @InjectMocks
    private JsonRequestValidator requestValidator;

    @Test
    void Validate_ShouldReturnIsNull_WhenRequestIsNull() {
        String request = null;

        RequestValidationResult result = requestValidator.validate(request);

        assertEquals(RequestValidationResult.IS_NULL, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "plain text",
            "{\"key\": \"value\"",
            "{key\": \"value\"}",
            "[{\"key\": \"value\"}, {key: \"value\"}]",
            "null",
            "123",
            "[1, 2, 3]"})
    void Validate_ShouldReturnBadFormat_WhenTextIsNotValidJsonFormat(String text) {
        RequestValidationResult result = requestValidator.validate(text);

        assertEquals(RequestValidationResult.BAD_FORMAT, result);
    }

    @Test
    void Validate_ShouldReturnNoCommand_WhenJsonDoesNotContainCommandKeyValuePair() {
        when(commandExtractor.extract(any())).thenReturn(Optional.empty());

        RequestValidationResult result = requestValidator.validate("{}");

        assertEquals(RequestValidationResult.NO_COMMAND, result);
    }

    @Test
    void Validate_ShouldReturnOk_WhenJsonContainsCommandKeyValuePair() {
        when(commandExtractor.extract(any())).thenReturn(Optional.of("cancelReservation"));

        RequestValidationResult result = requestValidator.validate("{}");

        assertEquals(RequestValidationResult.OK, result);
    }

}
