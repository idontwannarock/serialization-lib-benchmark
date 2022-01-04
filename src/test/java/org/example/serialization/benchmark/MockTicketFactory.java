package org.example.serialization.benchmark;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.example.serialization.benchmark.helper.MockDataProducer.*;

public class MockTicketFactory implements MockFactory<Integer, Ticket> {

    private final int ROUNDS;

    public MockTicketFactory(int rounds) {
        this.ROUNDS = rounds;
    }

    @Override
    public Ticket generate(Integer round) {
        final Ticket.Location departure = generateDeparture(round);
        final OffsetDateTime departureTime = generateDepartureTime();
        final Ticket.Location arrival = generateArrival(round);
        final OffsetDateTime arrivalTime = generateArrivalTime(arrival, departureTime);
        final BigDecimal price = generatePrice(arrival);
        return Ticket.builder()
                .id(round + ROUNDS)
                .transportation(generateTransportation())
                .currency(Ticket.Currency.USD)
                .departure(departure)
                .departureTime(departureTime)
                .arrival(arrival)
                .arrivalTime(arrivalTime)
                .price(price)
                .build();
    }

    private Ticket.Transportation generateTransportation() {
        return Ticket.Transportation.AIRLINE;
    }

    private Ticket.Location generateDeparture(Integer round) {
        return round / 2 == 0 ? Ticket.Location.TSA : Ticket.Location.TPE;
    }

    private OffsetDateTime generateDepartureTime() {
        return OffsetDateTime.now();
    }

    private Ticket.Location generateArrival(Integer round) {
        return round / 2 == 0 ? Ticket.Location.NRT : Ticket.Location.LAX;
    }

    private OffsetDateTime generateArrivalTime(Ticket.Location arrival, OffsetDateTime departureTime) {
        if (arrival == Ticket.Location.NRT) {
            return departureTime.plusHours(5);
        } else {
            return departureTime.plusHours(13);
        }
    }

    private BigDecimal generatePrice(Ticket.Location arrival) {
        if (arrival == Ticket.Location.NRT) {
            return generateRandomNrtTicketPrice();
        } else {
            return generateRandomLaxTicketPrice();
        }
    }
}
