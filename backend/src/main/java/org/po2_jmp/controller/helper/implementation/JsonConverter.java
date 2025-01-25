package org.po2_jmp.controller.helper.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonConverter {

    private final ObjectMapper mapper;

    public JsonConverter() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public String serialize(Object object) throws JsonProcessingException {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

    public <T> T deserialize(String json, Class<T> objectClass)
            throws JsonProcessingException {
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        return mapper.readValue(json, objectClass);
    }

}
