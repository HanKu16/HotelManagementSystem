package org.po2_jmp.controller.helper.implementation;

import org.json.JSONException;
import org.json.JSONObject;
import org.po2_jmp.controller.helper.contract.CommandExtractor;
import java.util.Optional;

public class JsonCommandExtractor implements CommandExtractor {

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
