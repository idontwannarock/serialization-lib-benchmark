package com.example.serialization.benchmark;

import com.example.serialization.benchmark.gson.GsonSerializer;
import com.example.serialization.benchmark.gson.GsonSmallObject;
import org.junit.Test;

public class SerializeDeserializeTest {

    private final static int ROUNDS = 1_000_000;

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    public void gson_serialize_deserialize_small_object_test() {
        // arrange
        GsonSmallObject object = new GsonSmallObject().id(1).firstName("Hello").lastName("World");
        long totalCostTimeInMilliseconds = 0;

        // act
        for (int round = 0; round < ROUNDS; round++) {
            long startTime = System.currentTimeMillis();

            try {
                // serialize object
                String serializedResult = serializer.serialize(object);

                // deserialize string
                serializer.deserialize(serializedResult);
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalCostTimeInMilliseconds += (System.currentTimeMillis() - startTime);
        }
        System.out.print("Using Gson to serialize/deserialize small object " + ROUNDS + " times costs total of " + totalCostTimeInMilliseconds + " milliseconds");
        System.out.println(", averaging " + ((float) totalCostTimeInMilliseconds / ROUNDS ) + " milliseconds per serialize and deserialize.");
    }

}
