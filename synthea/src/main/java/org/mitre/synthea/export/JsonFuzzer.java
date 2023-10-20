package org.mitre.synthea.export;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JsonFuzzer {

    private static Random rand = new Random();

    public static String fuzzJson(String jsonString) {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonObject fuzzedObject = fuzzJsonObject(jsonObject);
        return fuzzedObject.toString();
    }

    private static JsonObject fuzzJsonObject(JsonObject jsonObject) {
        Map<String, JsonElement> changes = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            if (rand.nextBoolean()) {
                changes.put(fuzzString(key), value);
            } else {
                if (value.isJsonObject()) {
                    changes.put(key, fuzzJsonObject(value.getAsJsonObject()));
                } else if (value.isJsonArray()) {
                    changes.put(key, fuzzJsonArray(value.getAsJsonArray()));
                } else {
                    changes.put(key, fuzzValue(value));
                }
            }
        }

        for (Map.Entry<String, JsonElement> change : changes.entrySet()) {
            jsonObject.remove(change.getKey());
            jsonObject.add(change.getKey(), change.getValue());
        }

        return jsonObject;
    }

    private static JsonArray fuzzJsonArray(JsonArray jsonArray) {
        JsonArray fuzzedArray = new JsonArray();

        for (JsonElement element : jsonArray) {
            if (element.isJsonObject()) {
                fuzzedArray.add(fuzzJsonObject(element.getAsJsonObject()));
            } else if (element.isJsonArray()) {
                fuzzedArray.add(fuzzJsonArray(element.getAsJsonArray()));
            } else {
                fuzzedArray.add(fuzzValue(element));
            }
        }

        return fuzzedArray;
    }

    private static String fuzzString(String input) {
        int position = rand.nextInt(input.length());
        char randomChar = (char) (rand.nextInt(26) + 'a');
        return input.substring(0, position) + randomChar + input.substring(position);
    }

    private static JsonElement fuzzValue(JsonElement value) {
        if (value.isJsonPrimitive()) {
            JsonPrimitive primitive = value.getAsJsonPrimitive();
            if (primitive.isString()) {
                return new JsonPrimitive(fuzzString(primitive.getAsString()));
            }
        }
        return value;
    }

    public static void main(String[] args) {
        String jsonString = "{...}"; // The provided JSON string
        System.out.println(fuzzJson(jsonString));
    }
}