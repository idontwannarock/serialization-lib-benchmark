package org.example.serialization.benchmark;

import org.example.serialization.benchmark.gson.GsonPassenger;
import org.example.serialization.benchmark.gson.GsonPassengerMockFactory;
import org.example.serialization.benchmark.gson.GsonSerializer;
import org.example.serialization.benchmark.helper.TablePrinter;
import org.example.serialization.benchmark.protobuf.ProtoBufPassengerMockFactory;
import org.example.serialization.benchmark.protobuf.ProtoBufSerializer;
import org.example.serialization.benchmark.protobuf.ProtoPassenger;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SerializeDeserializeTest {

    private final static int ROUNDS = 1_000_000;

    private final static TablePrinter printer = new TablePrinter(ROUNDS);

    private final GsonSerializer gsonSerializer = new GsonSerializer();
    private final ProtoBufSerializer protoBufSerializer = new ProtoBufSerializer();

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
                String serializedResult = gsonSerializer.serialize(passenger);
                totalSerializationCostInMillis += (System.currentTimeMillis() - startTime);

                // deserialize string
                startTime = System.currentTimeMillis();
                GsonPassenger result = gsonSerializer.deserialize(serializedResult);
                totalDeserializationCostInMillis += (System.currentTimeMillis() - startTime);

                // assert
                assertEquals(passenger, result);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        printer.printResult("Gson", totalSerializationCostInMillis, totalDeserializationCostInMillis);
    }

    @Test
    public void protobuf_serialize_deserialize_test() {
        PassengerMockFactory<ProtoPassenger> passengerMockFactory = new ProtoBufPassengerMockFactory(ROUNDS);
        ProtoPassenger passenger;
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
                byte[] serializedResult = protoBufSerializer.serialize(passenger);
                totalSerializationCostInMillis += (System.currentTimeMillis() - startTime);

                // deserialize string
                startTime = System.currentTimeMillis();
                ProtoPassenger result = protoBufSerializer.deserialize(serializedResult);
                totalDeserializationCostInMillis += (System.currentTimeMillis() - startTime);

                // assert
                assertEquals(passenger, result);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        printer.printResult("ProtoBuf", totalSerializationCostInMillis, totalDeserializationCostInMillis);
    }

}
