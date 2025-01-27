package org.po2_jmp.controller.helper.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.po2_jmp.controller.helper.RequestValidationResult;
import org.po2_jmp.controller.helper.contract.InvalidRequestResponder;
import org.po2_jmp.response.Response;
import org.po2_jmp.response.ResponseStatus;

/**
 * Implementation of the {@link InvalidRequestResponder} interface for
 * generating responses for invalid requests.
 * <p>
 * This class generates JSON responses for different types of invalid requests,
 * such as null requests, requests with bad format, or requests without a command.
 */
public class InvalidRequestResponderImpl implements InvalidRequestResponder {

    private final JsonConverter jsonConverter;

    /**
     * Constructs an {@link InvalidRequestResponderImpl} with the
     * provided {@link JsonConverter}.
     *
     * @param jsonConverter the {@link JsonConverter} to serialize the response objects
     * @throws IllegalArgumentException if the provided {@link JsonConverter} is null
     */
    public InvalidRequestResponderImpl(JsonConverter jsonConverter) {
        if (jsonConverter == null) {
            throw new IllegalArgumentException("JsonConverter can not be null," +
                    " but null was passed to InvalidRequestResponderImpl");
        }
        this.jsonConverter = jsonConverter;
    }

    /**
     * Responds to the given {@link RequestValidationResult} by generating an appropriate
     * response message in JSON format.
     *
     * @param validationResult the result of the request validation
     * @return a JSON string representing the response to the invalid request
     * @throws IllegalArgumentException if the validation result is OK,
     * which should be handled differently
     */
    public String respond(RequestValidationResult validationResult) {
        return switch (validationResult) {
            case BAD_FORMAT -> handleBadRequestFormat();
            case NO_COMMAND -> handleRequestWithoutCommand();
            case IS_NULL -> handleNullRequest();
            case OK -> throw new IllegalArgumentException("RequestValidationResult " +
                    "is OK and should be handled in different way");
        };
    }

    private String handleNullRequest() {
        String response;
        try {
            Response invalidRequestResponse = new Response(
                    ResponseStatus.BAD_REQUEST, "request is null");
            response = jsonConverter.serialize(invalidRequestResponse);
        } catch (JsonProcessingException e) {
            response = "{\"status\": \"BAD_REQUEST\", " +
                    "\"message\": \"request is null\"}";
        }
        return response;
    }

    private String handleBadRequestFormat() {
        String response;
        try {
            Response invalidRequestResponse = new Response(
                    ResponseStatus.BAD_REQUEST, "invalid request format");
            response = jsonConverter.serialize(invalidRequestResponse);
        } catch (JsonProcessingException e) {
            response = "{\"status\": \"BAD_REQUEST\", " +
                    "\"message\": \"invalid request format\"}";
        }
        return response;
    }

    private String handleRequestWithoutCommand() {
        String response;
        try {
            Response invalidRequestResponse = new Response(
                    ResponseStatus.METHOD_NOT_ALLOWED,
                    "command was not specified in request");
            response = jsonConverter.serialize(invalidRequestResponse);
        } catch (JsonProcessingException e) {
            response = "{\"status\": \"METHOD_NOT_ALLOWED\", " +
                    "\"message\": \"command was not specified in request\"}";
        }
        return response;
    }

}
