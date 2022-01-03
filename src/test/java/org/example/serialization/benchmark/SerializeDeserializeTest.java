package org.example.serialization.benchmark;

import org.example.serialization.benchmark.flatbuf.*;
import org.example.serialization.benchmark.gson.GsonPassenger;
import org.example.serialization.benchmark.gson.GsonPassengerMockFactory;
import org.example.serialization.benchmark.gson.GsonSerializer;
import org.example.serialization.benchmark.helper.TablePrinter;
import org.example.serialization.benchmark.protobuf.ProtoBufPassengerMockFactory;
import org.example.serialization.benchmark.protobuf.ProtoBufSerializer;
import org.example.serialization.benchmark.protobuf.ProtoPassenger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.example.serialization.benchmark.helper.MockDataProducer.generateMockGender;
import static org.junit.Assert.assertEquals;

public class SerializeDeserializeTest {

    private final static int ROUNDS = 1_000_000;

    private final static TablePrinter printer = new TablePrinter(ROUNDS);

    private final GsonSerializer gsonSerializer = new GsonSerializer();
    private final ProtoBufSerializer protoBufSerializer = new ProtoBufSerializer();
    private final FlatBufSerializer flatBufSerializer = new FlatBufSerializer();

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
                startTime = System.currentTimeMillis();
                passenger = passengerMockFactory.generateMockPassenger(round);

                // serialize object
                String serializedResult = gsonSerializer.serialize(passenger);
                totalSerializationCostInMillis += (System.currentTimeMillis() - startTime);

                // deserialize string
                startTime = System.currentTimeMillis();
                GsonPassenger result = gsonSerializer.deserialize(serializedResult);
                totalDeserializationCostInMillis += (System.currentTimeMillis() - startTime);

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
                startTime = System.currentTimeMillis();
                passenger = passengerMockFactory.generateMockPassenger(round);

                // serialize object
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

    @Test
    public void flatbuf_serialize_deserialize_test() {
        PassengerMockFactory<ByteBuffer> passengerMockFactory = new FlatBufPassengerMockFactory(ROUNDS);
        ByteBuffer passenger;
        long startTime;
        long totalSerializationCostInMillis = 0;
        long totalDeserializationCostInMillis = 0;

        for (int round = 0; round < ROUNDS; round++) {
            try {
                startTime = System.currentTimeMillis();
                // serialize object
                passenger = passengerMockFactory.generateMockPassenger(round);
                totalSerializationCostInMillis += (System.currentTimeMillis() - startTime);

                // deserialize string
                startTime = System.currentTimeMillis();
                FlatPassenger result = flatBufSerializer.deserialize(passenger);
                totalDeserializationCostInMillis += (System.currentTimeMillis() - startTime);

                assertEquals(round + 1, result.id());
                assertEquals("Hello", result.firstName());
                assertEquals("World", result.lastName());
                assertEquals(generateMockGender(round), result.isMale());
                assertEquals(1, result.belongings(0).id());
                assertEquals(generateMockBelongingType(round), result.belongings(0).type());
                assertEquals(round + ROUNDS, result.ticket().id());
                assertEquals(FlatTransportation.AIRLINE, result.ticket().transportation());
                assertEquals(generateMockDeparture(round), result.ticket().departure());
                assertEquals(generateMockArrival(round), result.ticket().arrival());
                assertEquals(FlatCurrency.USD, result.ticket().currency());
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        printer.printResult("FlatBuf", totalSerializationCostInMillis, totalDeserializationCostInMillis);
    }

    private int generateMockBelongingType(int round) {
        return round / 2 == 0 ? FlatBelongingType.SUITCASE : FlatBelongingType.BACKPACK;
    }

    private int generateMockDeparture(int round) {
        return round / 2 == 0 ? FlatLocation.TSA : FlatLocation.TPE;
    }

    private int generateMockArrival(int round) {
        return round / 2 == 0 ? FlatLocation.NRT : FlatLocation.LAX;
    }
}
