package org.po2_jmp.controller.helper.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Class for serializing and deserializing Java objects to and from JSON.
 * <p>
 * This class uses the Jackson {@link ObjectMapper} to convert Java objects to JSON
 * and vice versa. Additionally, it includes options to serialize non-null
 * fields only during serialization.
 */
public class JsonConverter {

    private final ObjectMapper mapper;

    /**
     * Constructs a new {@link JsonConverter} instance with default configuration.
     */
    public JsonConverter() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Serializes a Java object to its JSON representation as a string.
     *
     * @param object the Java object to serialize
     * @return a JSON string representing the serialized object
     * @throws JsonProcessingException if there is an error during serialization
     */
    public String serialize(Object object) throws JsonProcessingException {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

    /**
     * Deserializes a JSON string into a Java object of the specified class.
     *
     * @param json the JSON string to deserialize
     * @param objectClass the class of the object to deserialize to
     * @param <T> the type of the resulting object
     * @return the deserialized Java object
     * @throws JsonProcessingException if there is an error during deserialization
     */
    public <T> T deserialize(String json, Class<T> objectClass)
            throws JsonProcessingException {
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        return mapper.readValue(json, objectClass);
    }

}
