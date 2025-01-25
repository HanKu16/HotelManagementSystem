package org.po2_jmp.controller.helper.implementation;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JsonCommandExtractorTest {

    private final JsonCommandExtractor commandExtractor = new JsonCommandExtractor();

    @Test
    void Extract_ShouldReturnCommand_WhenValidJsonWithCommandKeyProvided() {
        String validJson = "{\"command\": \"testCommand\"}";

        Optional<String> result = commandExtractor.extract(validJson);

        assertTrue(result.isPresent());
    }

    @Test
    void Extract_ShouldReturnEmptyOptional_WhenValidJsonWithoutCommandKeyProvided() {
        String validJsonWithoutCommand = "{\"key\": \"value\"}";

        Optional<String> result = commandExtractor.extract(validJsonWithoutCommand);

        assertTrue(result.isEmpty());
    }

    @Test
    void Extract_ShouldThrowJSONException_WhenEmptyStringProvided() {
        String emptyString = "";

        assertThrows(JSONException.class, () -> commandExtractor.extract(emptyString));
    }

    @Test
    void Extract_ShouldReturnEmptyOptional_WhenCommandKeyIsMissingInJsonObject() {
        String validJsonWithoutCommand = "{\"name\": \"test\"}";

        Optional<String> result = commandExtractor.extract(validJsonWithoutCommand);

        assertTrue(result.isEmpty());
    }

}