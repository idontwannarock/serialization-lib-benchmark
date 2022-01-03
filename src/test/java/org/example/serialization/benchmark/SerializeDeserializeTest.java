package org.example.serialization.benchmark;

import com.google.common.testing.GcFinalization;
import org.example.serialization.benchmark.flatbuf.*;
import org.example.serialization.benchmark.gson.*;
import org.example.serialization.benchmark.helper.TablePrinter;
import org.example.serialization.benchmark.protobuf.*;
import org.junit.After;
import org.junit.Before;
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
        PassengerMockFactory<GsonPassenger> passengerMockFactory = new GsonPassengerMockFactory(ROUNDS);
        GsonPassenger passenger;
        long startTime;
        long totalSerializationCostInMillis = 0;
        long totalDeserializationCostInMillis = 0;
        long maxUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        for (int round = 0; round < ROUNDS; round++) {
            try {
                startTime = System.currentTimeMillis();
                passenger = passengerMockFactory.generateMockPassenger(round);

                // serialize object
                String serializedResult = gsonSerializer.serialize(passenger);
                totalSerializationCostInMillis += (System.currentTimeMillis() - startTime);
                maxUsedMemory = Math.max(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), maxUsedMemory);

                // deserialize string
                startTime = System.currentTimeMillis();
                GsonPassenger result = gsonSerializer.deserialize(serializedResult);
                assertEquals(passenger, result);
                assertEquals(Integer.valueOf(round + 1), result.getId());
                assertEquals("Hello", result.getFirstName());
                assertEquals("World", result.getLastName());
                assertEquals(generateMockGender(round), result.getIsMale());
                assertEquals(Integer.valueOf(1), result.getBelongings()[0].getId());
                assertEquals(generateMockGsonBelongingType(round), result.getBelongings()[0].getType());
                assertEquals(Integer.valueOf(round + ROUNDS), result.getTicket().getId());
                assertEquals(GsonTicket.Transportation.AIRLINE, result.getTicket().getTransportation());
                assertEquals(generateMockGsonDeparture(round), result.getTicket().getDeparture());
                assertEquals(generateMockGsonArrival(round), result.getTicket().getArrival());
                assertEquals(GsonTicket.Currency.USD, result.getTicket().getCurrency());
                totalDeserializationCostInMillis += (System.currentTimeMillis() - startTime);
                maxUsedMemory = Math.max(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), maxUsedMemory);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        printer.printResult("Gson", totalSerializationCostInMillis, totalDeserializationCostInMillis, maxUsedMemory);
    }

    private GsonBelonging.BelongingType generateMockGsonBelongingType(int round) {
        return round / 2 == 0 ? GsonBelonging.BelongingType.SUITCASE : GsonBelonging.BelongingType.BACKPACK;
    }

    private GsonTicket.Location generateMockGsonDeparture(int round) {
        return round / 2 == 0 ? GsonTicket.Location.TSA : GsonTicket.Location.TPE;
    }

    private GsonTicket.Location generateMockGsonArrival(int round) {
        return round / 2 == 0 ? GsonTicket.Location.NRT : GsonTicket.Location.LAX;
    }

    @Test
    public void protobuf_serialize_deserialize_test() {
        PassengerMockFactory<ProtoPassenger> passengerMockFactory = new ProtoBufPassengerMockFactory(ROUNDS);
        ProtoPassenger passenger;
        long startTime;
        long totalSerializationCostInMillis = 0;
        long totalDeserializationCostInMillis = 0;
        long maxUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        for (int round = 0; round < ROUNDS; round++) {
            try {
                startTime = System.currentTimeMillis();
                passenger = passengerMockFactory.generateMockPassenger(round);

                // serialize object
                byte[] serializedResult = protoBufSerializer.serialize(passenger);
                totalSerializationCostInMillis += (System.currentTimeMillis() - startTime);
                maxUsedMemory = Math.max(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), maxUsedMemory);

                // deserialize string
                startTime = System.currentTimeMillis();
                ProtoPassenger result = protoBufSerializer.deserialize(serializedResult);
                assertEquals(passenger, result);
                assertEquals(round + 1, result.getId());
                assertEquals("Hello", result.getFirstName());
                assertEquals("World", result.getLastName());
                assertEquals(generateMockGender(round), result.getIsMale());
                assertEquals(1, result.getBelongings(0).getId());
                assertEquals(generateMockProtoBelongingType(round), result.getBelongings(0).getType());
                assertEquals(round + ROUNDS, result.getTicket().getId());
                assertEquals(ProtoTicket.Transportation.AIRLINE, result.getTicket().getTransportation());
                assertEquals(generateMockProtoDeparture(round), result.getTicket().getDeparture());
                assertEquals(generateMockProtoArrival(round), result.getTicket().getArrival());
                assertEquals(ProtoTicket.Currency.USD, result.getTicket().getCurrency());
                totalDeserializationCostInMillis += (System.currentTimeMillis() - startTime);
                maxUsedMemory = Math.max(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), maxUsedMemory);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        printer.printResult("ProtoBuf", totalSerializationCostInMillis, totalDeserializationCostInMillis, maxUsedMemory);
    }

    private ProtoBelonging.BelongingType generateMockProtoBelongingType(int round) {
        return round / 2 == 0 ? ProtoBelonging.BelongingType.SUITCASE : ProtoBelonging.BelongingType.BACKPACK;
    }

    private ProtoTicket.Location generateMockProtoDeparture(int round) {
        return round / 2 == 0 ? ProtoTicket.Location.TSA : ProtoTicket.Location.TPE;
    }

    private ProtoTicket.Location generateMockProtoArrival(int round) {
        return round / 2 == 0 ? ProtoTicket.Location.NRT : ProtoTicket.Location.LAX;
    }

    @Test
    public void flatbuf_serialize_deserialize_test() {
        PassengerMockFactory<ByteBuffer> passengerMockFactory = new FlatBufPassengerMockFactory(ROUNDS);
        ByteBuffer passenger;
        long startTime;
        long totalSerializationCostInMillis = 0;
        long totalDeserializationCostInMillis = 0;
        long maxUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        for (int round = 0; round < ROUNDS; round++) {
            try {
                startTime = System.currentTimeMillis();
                // serialize object
                passenger = passengerMockFactory.generateMockPassenger(round);
                totalSerializationCostInMillis += (System.currentTimeMillis() - startTime);
                maxUsedMemory = Math.max(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), maxUsedMemory);

                // deserialize string
                startTime = System.currentTimeMillis();
                FlatPassenger result = flatBufSerializer.deserialize(passenger);
                assertEquals(round + 1, result.id());
                assertEquals("Hello", result.firstName());
                assertEquals("World", result.lastName());
                assertEquals(generateMockGender(round), result.isMale());
                assertEquals(1, result.belongings(0).id());
                assertEquals(generateMockFlatBelongingType(round), result.belongings(0).type());
                assertEquals(round + ROUNDS, result.ticket().id());
                assertEquals(FlatTransportation.AIRLINE, result.ticket().transportation());
                assertEquals(generateMockFlatDeparture(round), result.ticket().departure());
                assertEquals(generateMockFlatArrival(round), result.ticket().arrival());
                assertEquals(FlatCurrency.USD, result.ticket().currency());
                totalDeserializationCostInMillis += (System.currentTimeMillis() - startTime);
                maxUsedMemory = Math.max(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), maxUsedMemory);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        printer.printResult("FlatBuf", totalSerializationCostInMillis, totalDeserializationCostInMillis, maxUsedMemory);
    }

    private int generateMockFlatBelongingType(int round) {
        return round / 2 == 0 ? FlatBelongingType.SUITCASE : FlatBelongingType.BACKPACK;
    }

    private int generateMockFlatDeparture(int round) {
        return round / 2 == 0 ? FlatLocation.TSA : FlatLocation.TPE;
    }

    private int generateMockFlatArrival(int round) {
        return round / 2 == 0 ? FlatLocation.NRT : FlatLocation.LAX;
    }

    @After
    public void teardown() {
        //noinspection UnstableApiUsage
        GcFinalization.awaitFullGc();
    }
}
