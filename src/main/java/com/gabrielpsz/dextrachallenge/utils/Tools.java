package com.gabrielpsz.dextrachallenge.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Tools {

    public static String getStringJsonFieldValue(String responseBodyAsString, String fieldValue) {
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(responseBodyAsString).getAsJsonObject();
        JsonElement jsonElement = obj.get(fieldValue);
        if (jsonElement == null) {
            return null;
        }
        return jsonElement.getAsString();
    }

}
