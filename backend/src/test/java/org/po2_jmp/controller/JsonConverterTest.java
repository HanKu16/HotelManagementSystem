package org.po2_jmp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.po2_jmp.controller.helper.Person;
import static org.junit.jupiter.api.Assertions.*;

public class JsonConverterTest {

    private JsonConverter jsonConverter;

    @BeforeEach
    public void setUp() {
        jsonConverter = new JsonConverter();
    }

    @Test
    void Serialize_ShouldReturnValidJsonString_WhenPersonObjectIsValid()
            throws JsonProcessingException {
        Person person = new Person("John", 30);

        String json = jsonConverter.serialize(person);

        assertEquals("{\"name\":\"John\",\"age\":30}", json);
    }

    @Test
    void Deserialize_ShouldReturnValidObject_WhenJsonStringIsValid()
            throws JsonProcessingException {
        Person expectedPerson = new Person("John", 30);
        String json = "{\"name\":\"John\",\"age\":30}";

        Person person = jsonConverter.deserialize(json, Person.class);

        assertEquals(expectedPerson, person);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"John\",\"age\":30",
            "\"name\":\"John\",\"age\":30}",
            "{\"name\":\"John\"\"age\":30}",
            "{\"name\"\"John\",\"age\":30}",
            "{\"name\"\"John\",\"ag\":30}",
    })
    void Deserialize_ShouldThrowJsonProcessingException_WhenJsonStringIsInValid(String json)
            throws JsonProcessingException {
        Person expectedPerson = new Person("John", 30);

        assertThrows(JsonProcessingException.class, () -> {
            jsonConverter.deserialize(json, Person.class);
        });
    }

}
