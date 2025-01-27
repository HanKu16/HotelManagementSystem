package org.po2_jmp.controller.helper.implementation;

import org.json.JSONException;
import org.json.JSONObject;
import org.po2_jmp.controller.helper.contract.CommandExtractor;
import java.util.Optional;

/**
 * Implementation of the {@link CommandExtractor} interface for extracting
 * the command from a JSON-formatted request string.
 * <p>
 * This class converts the input request string into a {@link JSONObject} and
 * attempts to extract the command value from the JSON object. If the request
 * cannot be converted to a valid JSON object or the "command" field is missing,
 * an empty {@link Optional} is returned.
 */
public class JsonCommandExtractor implements CommandExtractor {

    /**
     * Extracts the command from a given JSON-formatted request string.
     * <p>
     * This method first tries to convert the request string into a {@link JSONObject}.
     * If the conversion is successful, it retrieves the "command" field. If either
     * the conversion fails or the "command" field is missing, an empty {@link Optional}
     * is returned.
     * </p>
     *
     * @param request the input request string in JSON format
     * @return an {@link Optional} containing the command if found, or an empty
     *          {@link Optional} if no command could be extracted
     * @throws JSONException if the request string cannot be converted to a
     *          {@link JSONObject}
     */
    public Optional<String> extract(String request) {
        Optional<JSONObject> optionalJSONObject = convertRequestToJsonObject(request);
        if (optionalJSONObject.isEmpty()) {
            throw new JSONException("Can not convert to " +
                    "JSONObject from "+ request + " String");
        }
        return getCommand(optionalJSONObject.get());
    }

    private Optional<JSONObject> convertRequestToJsonObject(String request) {
        Optional<JSONObject> optionalJsonObject;
        try {
            JSONObject jsonObject = new JSONObject(request);
            optionalJsonObject = Optional.of(jsonObject);
        } catch (JSONException e) {
            optionalJsonObject = Optional.empty();
        }
        return optionalJsonObject;
    }

    private Optional<String> getCommand(JSONObject jsonObject) {
        Optional<String> optionalCommand;
        try {
            String command = jsonObject.getString("command");
            optionalCommand = Optional.of(command);
        } catch (JSONException e) {
            optionalCommand = Optional.empty();
        }
        return optionalCommand;
    }

}
