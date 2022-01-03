package org.example.serialization.benchmark.protobuf;

import org.example.serialization.benchmark.PassengerMockFactory;

import java.util.List;

import static org.example.serialization.benchmark.helper.MockDataProducer.*;

public class ProtoBufPassengerMockFactory implements PassengerMockFactory<ProtoPassenger> {

    private final int ROUNDS;

    public ProtoBufPassengerMockFactory(int rounds) {
        this.ROUNDS = rounds;
    }

    @Override
    public ProtoPassenger generateMockPassenger(int round) {
        return ProtoPassenger.newBuilder()
                .setId(round + 1)
                .setFirstName("Hello")
                .setLastName("World")
                .setIsMale(generateMockGender(round))
                .addAllBelongings(generateMockBelongings(round))
                .setTicket(generateMockTicket(round))
                .build();
    }

    private List<ProtoBelonging> generateMockBelongings(int round) {
        return List.of(new ProtoBelonging[] {generateMockBelonging(round)});
    }

    private ProtoBelonging generateMockBelonging(int round) {
        ProtoBelonging.BelongingType type = generateMockBelongingType(round);
        return ProtoBelonging.newBuilder()
                .setId(1)
                .setType(type)
                .setWeightInKilogram(generateMockBelongingWeightWith(type))
                .build();
    }

    private ProtoBelonging.BelongingType generateMockBelongingType(int round) {
        return round / 2 == 0 ? ProtoBelonging.BelongingType.SUITCASE : ProtoBelonging.BelongingType.BACKPACK;
    }

    private float generateMockBelongingWeightWith(ProtoBelonging.BelongingType type) {
        if (type == ProtoBelonging.BelongingType.BACKPACK) {
            return generateRandomBackPackWeightInKilogram();
        } else {
            return generateRandomSuitcaseWeightInKilogram();
        }
    }

    private ProtoTicket generateMockTicket(int round) {
        String departureTime = generateMockDepartureTime(round);
        ProtoTicket.Location arrival = generateMockArrival(round);
        return ProtoTicket.newBuilder()
                .setId(round + ROUNDS)
                .setTransportation(generateMockTransportation())
                .setDeparture(generateMockDeparture(round))
                .setDepartureTime(departureTime)
                .setArrival(arrival)
                .setArrivalTime(generateMockArrivalTime(arrival, departureTime))
                .setCurrency(generateMockCurrency())
                .setPrice(generateTicketPrice(arrival))
                .build();
    }

    private ProtoTicket.Transportation generateMockTransportation() {
        return ProtoTicket.Transportation.AIRLINE;
    }

    private ProtoTicket.Location generateMockDeparture(int round) {
        return round / 2 == 0 ? ProtoTicket.Location.TSA : ProtoTicket.Location.TPE;
    }

    private ProtoTicket.Location generateMockArrival(int round) {
        return round / 2 == 0 ? ProtoTicket.Location.NRT : ProtoTicket.Location.LAX;
    }

    private String generateMockArrivalTime(ProtoTicket.Location arrival, String departureTime) {
        if (arrival == ProtoTicket.Location.NRT) {
            return generateMockNrtArrivalTime(departureTime);
        } else {
            return generateMockLaxArrivalTime(departureTime);
        }
    }

    private ProtoTicket.Currency generateMockCurrency() {
        return ProtoTicket.Currency.USD;
    }

    private String generateTicketPrice(ProtoTicket.Location arrival) {
        if (arrival == ProtoTicket.Location.NRT) {
            return generateRandomNrtTicketPrice();
        } else {
            return generateRandomLaxTicketPrice();
        }
    }
}
