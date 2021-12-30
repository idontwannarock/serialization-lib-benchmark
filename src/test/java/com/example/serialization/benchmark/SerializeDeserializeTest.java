package com.example.serialization.benchmark;

import com.example.serialization.benchmark.gson.GsonPassenger;
import com.example.serialization.benchmark.gson.GsonPassengerMockFactory;
import com.example.serialization.benchmark.gson.GsonSerializer;
import com.example.serialization.benchmark.helper.TablePrinter;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SerializeDeserializeTest {

    private final static int ROUNDS = 1_000_000;

    private final static TablePrinter printer = new TablePrinter(ROUNDS);

    private final GsonSerializer serializer = new GsonSerializer();

    @BeforeClass
    public static void setup() {
        printer.printHeader();
    }

    @Test
    public void gson_serialize_deserialize_test() {
        PassengerMockFactory<GsonPassenger> passengerMockFactory = new GsonPassengerMockFactory(ROUNDS);
        GsonPassenger passenger;
        long startTime;
        long totalSerializationCostInMillis = 0;
        long totalDeserializationCostInMillis = 0;

        for (int round = 0; round < ROUNDS; round++) {
            try {
                // arrange
                passenger = passengerMockFactory.generateMockPassenger(round);

                // act
                // serialize object
                startTime = System.currentTimeMillis();
                String serializedResult = serializer.serialize(passenger);
                totalSerializationCostInMillis += (System.currentTimeMillis() - startTime);

                // deserialize string
                startTime = System.currentTimeMillis();
                GsonPassenger result = serializer.deserialize(serializedResult);
                totalDeserializationCostInMillis += (System.currentTimeMillis() - startTime);

                // assert
                assertEquals(passenger, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        printer.printResult("Gson", totalSerializationCostInMillis, totalDeserializationCostInMillis);
    }
}
