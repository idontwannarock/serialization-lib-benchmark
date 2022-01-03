package org.example.serialization.benchmark.gson;

import org.example.serialization.benchmark.PassengerMockFactory;

import static org.example.serialization.benchmark.helper.MockDataProducer.*;

public class GsonPassengerMockFactory implements PassengerMockFactory<GsonPassenger> {

    private final int ROUNDS;

    public GsonPassengerMockFactory(int rounds) {
        this.ROUNDS = rounds;
    }

    @Override
    public GsonPassenger generateMockPassenger(int round) {
        final GsonPassenger passenger = new GsonPassenger();
        passenger.setId(round + 1);
        passenger.setFirstName("Hello");
        passenger.setLastName("World");
        passenger.setIsMale(generateMockGender(round));
        passenger.setBelongings(generateMockBelongings(round));
        passenger.setTicket(generateTicket(round));
        return passenger;
    }

    private GsonBelonging[] generateMockBelongings(int round) {
        return new GsonBelonging[] {generateMockBelonging(round)};
    }

    private GsonBelonging generateMockBelonging(int round) {
        GsonBelonging belonging = new GsonBelonging();
        belonging.setId(1);
        belonging.setType(generateMockBelongingType(round));
        belonging.setWeightInKilogram(generateMockBelongingWeightWith(belonging.getType()));
        return belonging;
    }

    private GsonBelonging.BelongingType generateMockBelongingType(int round) {
        return round / 2 == 0 ? GsonBelonging.BelongingType.SUITCASE : GsonBelonging.BelongingType.BACKPACK;
    }

    private int generateMockBelongingWeightWith(GsonBelonging.BelongingType type) {
        if (type == GsonBelonging.BelongingType.BACKPACK) {
            return generateRandomBackPackWeightInKilogram();
        } else {
            return generateRandomSuitcaseWeightInKilogram();
        }
    }

    private GsonTicket generateTicket(int round) {
        GsonTicket ticket = new GsonTicket();
        ticket.setId(round + ROUNDS);
        ticket.setTransportation(generateMockTransportation());
        ticket.setDeparture(generateMockDeparture(round));
        ticket.setDepartureTime(generateMockDepartureTime(round));
        ticket.setArrival(generateMockArrival(round));
        ticket.setArrivalTime(generateMockArrivalTime(ticket.getArrival(), ticket.getDepartureTime()));
        ticket.setCurrency(generateMockCurrency());
        ticket.setPrice(generateTicketPrice(ticket.getArrival()));
        return ticket;
    }

    private GsonTicket.Transportation generateMockTransportation() {
        return GsonTicket.Transportation.AIRLINE;
    }

    private GsonTicket.Location generateMockDeparture(int round) {
        return round / 2 == 0 ? GsonTicket.Location.TSA : GsonTicket.Location.TPE;
    }

    private GsonTicket.Location generateMockArrival(int round) {
        return round / 2 == 0 ? GsonTicket.Location.NRT : GsonTicket.Location.LAX;
    }

    private String generateMockArrivalTime(GsonTicket.Location arrival, String departureTime) {
        if (arrival == GsonTicket.Location.NRT) {
            return generateMockNrtArrivalTime(departureTime);
        } else {
            return generateMockLaxArrivalTime(departureTime);
        }
    }

    private GsonTicket.Currency generateMockCurrency() {
        return GsonTicket.Currency.USD;
    }

    private String generateTicketPrice(GsonTicket.Location arrival) {
        if (arrival == GsonTicket.Location.NRT) {
            return generateRandomNrtTicketPrice();
        } else {
            return generateRandomLaxTicketPrice();
        }
    }
}