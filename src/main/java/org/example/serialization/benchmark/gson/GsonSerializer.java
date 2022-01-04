package org.example.serialization.benchmark.gson;

import com.google.gson.Gson;
import org.example.serialization.benchmark.Passenger;

public class GsonSerializer {

    private final Gson gson = new Gson();

    private static final GsonPassengerMapper mapper = new GsonPassengerMapper();

    public String serialize(Passenger passenger) {
        return gson.toJson(mapper.map(passenger));
    }

    public Passenger deserialize(String serializedString) {
        return mapper.map(gson.fromJson(serializedString, GsonPassenger.class));
    }
}
