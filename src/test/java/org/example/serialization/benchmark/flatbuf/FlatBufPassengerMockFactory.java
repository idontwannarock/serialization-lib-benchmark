package org.example.serialization.benchmark.flatbuf;

import com.google.flatbuffers.FlatBufferBuilder;
import org.example.serialization.benchmark.PassengerMockFactory;
import org.example.serialization.benchmark.helper.MockDataProducer;

import java.nio.ByteBuffer;

import static org.example.serialization.benchmark.helper.MockDataProducer.*;

public class FlatBufPassengerMockFactory implements PassengerMockFactory<ByteBuffer> {

    private final int ROUNDS;

    public FlatBufPassengerMockFactory(int rounds) {
        this.ROUNDS = rounds;
    }

    @Override
    public ByteBuffer generateMockPassenger(int round) {
        FlatBufferBuilder builder = new FlatBufferBuilder(0);
        // Everything except structs MUST be created before the start of the table they are referenced in.
        int id = round + 1;
        int firstName = builder.createString("Hello");
        int lastName = builder.createString("World");
        boolean isMale = generateMockGender(round);
        int belongings = generateMockBelongings(builder, round);
        int ticket = generateMockTicket(builder, round);

        FlatPassenger.startFlatPassenger(builder);
        FlatPassenger.addId(builder, id);
        FlatPassenger.addFirstName(builder, firstName);
        FlatPassenger.addLastName(builder, lastName);
        FlatPassenger.addIsMale(builder, isMale);
        FlatPassenger.addBelongings(builder, belongings);
        FlatPassenger.addTicket(builder, ticket);
        int passenger = FlatPassenger.endFlatPassenger(builder);
        builder.finish(passenger);
        return builder.dataBuffer();
    }

    private int generateMockBelongings(FlatBufferBuilder builder, int round) {
        int id = 1;
        int type = generateMockBelongingType(round);
        float weightInKilogram = generateMockWeightInKilogram(type);

        int[] belongings = new int[1];
        FlatBelonging.startFlatBelonging(builder);
        FlatBelonging.addId(builder, id);
        FlatBelonging.addType(builder, type);
        FlatBelonging.addWeightInKilogram(builder, weightInKilogram);
        int belonging = FlatBelonging.endFlatBelonging(builder);
        belongings[0] = belonging;
        return FlatPassenger.createBelongingsVector(builder, belongings);
    }

    private int generateMockBelongingType(int round) {
        return round / 2 == 0 ? FlatBelongingType.SUITCASE : FlatBelongingType.BACKPACK;
    }

    private float generateMockWeightInKilogram(int type) {
        if (type == FlatBelongingType.BACKPACK) {
            return generateRandomBackPackWeightInKilogram();
        } else {
            return generateRandomSuitcaseWeightInKilogram();
        }
    }

    private int generateMockTicket(FlatBufferBuilder builder, int round) {
        int id = round + ROUNDS;
        int transportation = generateMockTransportation();
        int departure = generateMockDeparture(round);
        String departureTime = generateMockDepartureTime(round);
        int departureTimeOffset = convertMockDepartureTimeToOffset(builder, departureTime);
        int arrival = generateMockArrival(round);
        int arrivalTime = generateMockArrivalTime(builder, arrival, departureTime);
        int currency = generateMockCurrency();
        int price = generateMockPrice(builder, arrival);

        FlatTicket.startFlatTicket(builder);
        FlatTicket.addId(builder, id);
        FlatTicket.addTransportation(builder, transportation);
        FlatTicket.addDeparture(builder, departure);
        FlatTicket.addDepartureTime(builder, departureTimeOffset);
        FlatTicket.addArrival(builder, arrival);
        FlatTicket.addArrivalTime(builder, arrivalTime);
        FlatTicket.addCurrency(builder, currency);
        FlatTicket.addPrice(builder, price);
        return FlatTicket.endFlatTicket(builder);
    }

    private int generateMockTransportation() {
        return FlatTransportation.AIRLINE;
    }

    private int generateMockDeparture(int round) {
        return round / 2 == 0 ? FlatLocation.TSA : FlatLocation.TPE;
    }

    private String generateMockDepartureTime(int round) {
        return MockDataProducer.generateMockDepartureTime(round);
    }

    private int convertMockDepartureTimeToOffset(FlatBufferBuilder builder, String departureTime) {
        return builder.createString(departureTime);
    }

    private int generateMockArrival(int round) {
        return round / 2 == 0 ? FlatLocation.NRT : FlatLocation.LAX;
    }

    private int generateMockArrivalTime(FlatBufferBuilder builder, int arrival, String departureTime) {
        if (arrival == FlatLocation.NRT) {
            return builder.createString(generateMockNrtArrivalTime(departureTime));
        } else {
            return builder.createString(generateMockLaxArrivalTime(departureTime));
        }
    }

    private int generateMockCurrency() {
        return FlatCurrency.USD;
    }

    private int generateMockPrice(FlatBufferBuilder builder, int arrival) {
        if (arrival == FlatLocation.NRT) {
            return builder.createString(generateRandomNrtTicketPrice());
        } else {
            return builder.createString(generateRandomLaxTicketPrice());
        }
    }
}
