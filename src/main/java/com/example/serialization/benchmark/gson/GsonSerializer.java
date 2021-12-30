package com.example.serialization.benchmark.gson;

import com.google.gson.Gson;

public class GsonSerializer {

    private final Gson gson = new Gson();

    public String serialize(GsonSmallObject object) {
        return gson.toJson(object);
    }

    public GsonSmallObject deserialize(String serializedString) {
        return gson.fromJson(serializedString, GsonSmallObject.class);
    }
}
