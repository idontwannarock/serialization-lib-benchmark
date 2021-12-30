package org.example.serialization.benchmark.gson;

import org.example.serialization.benchmark.PassengerMockFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

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
        passenger.setIsMale(generateGender(round));
        passenger.setBelongings(generateMockBelongings(round));
        passenger.setTicket(generateTicket(round));
        return passenger;
    }

    private Boolean generateGender(int round) {
        return round / 2 == 0;
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
            return ThreadLocalRandom.current().nextInt(1_000, 5_000 + 1);
        } else {
            return ThreadLocalRandom.current().nextInt(10_000, 25_000 + 1);
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

    private GsonTicket.Location generateMockDeparture(int round) {
        return round / 2 == 0 ? GsonTicket.Location.TSA : GsonTicket.Location.TPE;
    }

    private LocalDateTime generateMockDepartureTime(int round) {
        return LocalDateTime.now().plusMinutes(round);
    }

    private GsonTicket.Location generateMockArrival(int round) {
        return round / 2 == 0 ? GsonTicket.Location.NRT : GsonTicket.Location.LAX;
    }

    private LocalDateTime generateMockArrivalTime(GsonTicket.Location arrival, LocalDateTime departureTime) {
        if (arrival == GsonTicket.Location.NRT) {
            return departureTime.plusHours(5);
        } else {
            return departureTime.plusHours(13);
        }
    }

    private GsonTicket.Transportation generateMockTransportation() {
        return GsonTicket.Transportation.AIRLINE;
    }

    private GsonTicket.Currency generateMockCurrency() {
        return GsonTicket.Currency.USD;
    }

    private BigDecimal generateTicketPrice(GsonTicket.Location arrival) {
        if (arrival == GsonTicket.Location.NRT) {
            return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(100.0, 1000.0));
        } else {
            return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1000.0, 5000.0));
        }
    }
}