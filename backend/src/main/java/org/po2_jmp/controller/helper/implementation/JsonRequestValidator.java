package org.po2_jmp.controller.helper.implementation;

import org.json.JSONException;
import org.json.JSONObject;
import org.po2_jmp.controller.helper.RequestValidationResult;
import org.po2_jmp.controller.helper.contract.CommandExtractor;
import org.po2_jmp.controller.helper.contract.RequestValidator;

import java.util.Optional;

public class JsonRequestValidator implements RequestValidator {

    private final CommandExtractor commandExtractor;

    public JsonRequestValidator(CommandExtractor commandExtractor) {
        if (commandExtractor == null) {
            throw new IllegalArgumentException("CommandExtractor can " +
                    "not be null, but null was passed to JsonRequestValidator");
        }
        this.commandExtractor = commandExtractor;
    }

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
