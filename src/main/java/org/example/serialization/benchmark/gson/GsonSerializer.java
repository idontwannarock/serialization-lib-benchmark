package org.example.serialization.benchmark.gson;

import com.google.gson.Gson;

public class GsonSerializer {

    private final Gson gson = new Gson();

    public String serialize(GsonPassenger object) {
        return gson.toJson(object);
    }

    public GsonPassenger deserialize(String serializedString) {
        return gson.fromJson(serializedString, GsonPassenger.class);
    }
}
