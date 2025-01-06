package org.po2_jmp.websocket;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils {

    private final ObjectMapper mapper;

    public JsonUtils() {
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
    public String getTypeFromMessage(String json) throws JsonProcessingException {
        JsonNode rootNode = mapper.readTree(json);
        JsonNode typeNode = rootNode.get("message");
        if (typeNode == null) {
            throw new IllegalArgumentException("Missing 'message' field in JSON");
        }
        return typeNode.asText();
    }


}
