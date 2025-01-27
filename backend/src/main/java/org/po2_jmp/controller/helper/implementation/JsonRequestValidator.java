package org.po2_jmp.controller.helper.implementation;

import org.json.JSONException;
import org.json.JSONObject;
import org.po2_jmp.controller.helper.RequestValidationResult;
import org.po2_jmp.controller.helper.contract.CommandExtractor;
import org.po2_jmp.controller.helper.contract.RequestValidator;
import java.util.Optional;

/**
 * Implementation of the {@link RequestValidator} interface for validating JSON-based requests.
 * <p>
 * This class validates a request string by checking its format, ensuring it can be
 * converted to a valid {@link JSONObject}, and verifying that it contains a valid command.
 */
public class JsonRequestValidator implements RequestValidator {

    private final CommandExtractor commandExtractor;

    /**
     * Constructs a {@link JsonRequestValidator} with the provided
     * {@link CommandExtractor}.
     * <p>
     * This constructor ensures that the provided {@link CommandExtractor}
     * is not null.
     *
     * @param commandExtractor the {@link CommandExtractor} used to extract
     *        commands from the request
     * @throws IllegalArgumentException if the provided {@link CommandExtractor} is null
     */
    public JsonRequestValidator(CommandExtractor commandExtractor) {
        if (commandExtractor == null) {
            throw new IllegalArgumentException("CommandExtractor can " +
                    "not be null, but null was passed to JsonRequestValidator");
        }
        this.commandExtractor = commandExtractor;
    }

    /**
     * Validates the given JSON request string.
     * <p>
     * This method performs the following checks:
     * <ul>
     *     <li>If the request is null, returns {@link RequestValidationResult#IS_NULL}.</li>
     *     <li>Attempts to convert the request into a valid {@link JSONObject}; if it fails,
     *     returns {@link RequestValidationResult#BAD_FORMAT}.</li>
     *     <li>Uses the {@link CommandExtractor} to extract the command from the request;
     *     if no command is found, returns {@link RequestValidationResult#NO_COMMAND}.</li>
     *     <li>If all checks pass, returns {@link RequestValidationResult#OK} indicating a
     *     valid request.</li>
     * </ul>
     *
     * @param request the JSON request string to validate
     * @return the validation result, indicating the status of the request
     */
    public RequestValidationResult validate(String request) {
        if (request == null) {
            return RequestValidationResult.IS_NULL;
        }
        Optional<JSONObject> optionalJSONObject = convertMessageToJsonObject(request);
        if (optionalJSONObject.isEmpty()) {
            return RequestValidationResult.BAD_FORMAT;
        }
        Optional<String> command = commandExtractor.extract(request);
        if (command.isEmpty()) {
            return RequestValidationResult.NO_COMMAND;
        }
        return RequestValidationResult.OK;
    }

    private Optional<JSONObject> convertMessageToJsonObject(String message) {
        Optional<JSONObject> optionalJsonObject;
        try {
            JSONObject jsonObject = new JSONObject(message);
            optionalJsonObject = Optional.of(jsonObject);
        } catch (JSONException e) {
            optionalJsonObject = Optional.empty();
        }
        return optionalJsonObject;
    }

}
