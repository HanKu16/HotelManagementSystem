package org.po2_jmp.websocket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * A utility class for serializing and deserializing objects to and from JSON.
 */
public class JsonUtils {

    private final ObjectMapper mapper;

    public JsonUtils() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Serializes an object to its JSON string representation.
     *
     * @return A JSON string representation of the object.
     */
    public String serialize(Object object) throws JsonProcessingException {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

    /**
     * Deserializes a JSON string into an object of the specified type.
     *
     * @return The deserialized object.
     */
    public <T> T deserialize(String json, Class<T> objectClass)
            throws JsonProcessingException {
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        return mapper.readValue(json, objectClass);
    }
}
