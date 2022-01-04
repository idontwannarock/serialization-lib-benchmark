package org.example.serialization.benchmark;

import com.google.common.testing.GcFinalization;
import org.example.serialization.benchmark.flatbuf.FlatBufSerializer;
import org.example.serialization.benchmark.gson.GsonSerializer;
import org.example.serialization.benchmark.helper.TablePrinter;
import org.example.serialization.benchmark.protobuf.ProtoBufSerializer;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class SerializeDeserializeTest {

    private final static int ROUNDS = 1_000_000;

    private static final TablePrinter printer = new TablePrinter(ROUNDS);
    private static final MockFactory<Integer, Passenger> mockPassengerFactory = new MockPassengerFactory(ROUNDS);

    private final GsonSerializer gsonSerializer = new GsonSerializer();
    private final ProtoBufSerializer protoBufSerializer = new ProtoBufSerializer();
    private final FlatBufSerializer flatBufSerializer = new FlatBufSerializer();

    @BeforeClass
    public static void startup() {
        printer.printHeader();
    }

    @Before
    public void setup() {
        //noinspection UnstableApiUsage
        GcFinalization.awaitFullGc();
    }

    @Test
    public void gson_serialize_deserialize_test() {
        Passenger passenger;
        long startTime;
        long totalSerializationCostInMillis = 0;
        long totalDeserializationCostInMillis = 0;
        long maxUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        for (int round = 0; round < ROUNDS; round++) {
            try {
                // arrange
                passenger = mockPassengerFactory.generate(round);

                // act
                // serialize object
                startTime = System.currentTimeMillis();
                String serializedResult = gsonSerializer.serialize(passenger);
                totalSerializationCostInMillis += (System.currentTimeMillis() - startTime);
                maxUsedMemory = Math.max(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), maxUsedMemory);

                // deserialize string
                startTime = System.currentTimeMillis();
                Passenger result = gsonSerializer.deserialize(serializedResult);
                totalDeserializationCostInMillis += (System.currentTimeMillis() - startTime);
                maxUsedMemory = Math.max(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), maxUsedMemory);

                // assert
                assertEquals(passenger, result);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        printer.printResult("Gson", totalSerializationCostInMillis, totalDeserializationCostInMillis, maxUsedMemory);
    }

    @Test
    public void protobuf_serialize_deserialize_test() {
        Passenger passenger;
        long startTime;
        long totalSerializationCostInMillis = 0;
        long totalDeserializationCostInMillis = 0;
        long maxUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        for (int round = 0; round < ROUNDS; round++) {
            try {
                // arrange
                passenger = mockPassengerFactory.generate(round);

                // act
                // serialize object
                startTime = System.currentTimeMillis();
                byte[] serializedResult = protoBufSerializer.serialize(passenger);
                totalSerializationCostInMillis += (System.currentTimeMillis() - startTime);
                maxUsedMemory = Math.max(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), maxUsedMemory);

                // deserialize string
                startTime = System.currentTimeMillis();
                Passenger result = protoBufSerializer.deserialize(serializedResult);
                totalDeserializationCostInMillis += (System.currentTimeMillis() - startTime);
                maxUsedMemory = Math.max(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), maxUsedMemory);

                // assert
                assertEquals(passenger, result);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        printer.printResult("ProtoBuf", totalSerializationCostInMillis, totalDeserializationCostInMillis, maxUsedMemory);
    }

    @Test
    public void flatbuf_serialize_deserialize_test() {
        Passenger passenger;
        long startTime;
        long totalSerializationCostInMillis = 0;
        long totalDeserializationCostInMillis = 0;
        long maxUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        for (int round = 0; round < ROUNDS; round++) {
            try {
                // arrange
                passenger = mockPassengerFactory.generate(round);

                // act
                // serialize object
                startTime = System.currentTimeMillis();
                ByteBuffer serializedResult = flatBufSerializer.serialize(passenger);
                totalSerializationCostInMillis += (System.currentTimeMillis() - startTime);
                maxUsedMemory = Math.max(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), maxUsedMemory);

                // deserialize string
                startTime = System.currentTimeMillis();
                Passenger result = flatBufSerializer.deserialize(serializedResult);
                totalDeserializationCostInMillis += (System.currentTimeMillis() - startTime);
                maxUsedMemory = Math.max(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), maxUsedMemory);

                // assert
                assertEquals(passenger, result);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        printer.printResult("FlatBuf", totalSerializationCostInMillis, totalDeserializationCostInMillis, maxUsedMemory);
    }

    @After
    public void teardown() {
        //noinspection UnstableApiUsage
        GcFinalization.awaitFullGc();
    }
}
