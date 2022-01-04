package org.example.serialization.benchmark;

import java.util.List;

import static org.example.serialization.benchmark.helper.MockDataProducer.generateMockGender;

public class MockPassengerFactory implements MockFactory<Integer, Passenger> {

    private final MockFactory<Integer, List<Belonging>> mockBelongingFactory;
    private final MockFactory<Integer, Ticket> mockTicketFactory;

    public MockPassengerFactory(int rounds) {
        this.mockBelongingFactory = new MockBelongingFactory();
        this.mockTicketFactory = new MockTicketFactory(rounds);
    }

    @Override
    public Passenger generate(Integer round) {
        return Passenger.builder()
                .id(round + 1)
                .firstName("Hello")
                .lastName("World")
                .isMale(generateGender(round))
                .belongings(generateBelongings(round))
                .ticket(generateTicket(round))
                .build();
    }

    private Boolean generateGender(int round) {
        return generateMockGender(round);
    }

    private List<Belonging> generateBelongings(int round) {
        return mockBelongingFactory.generate(round);
    }

    private Ticket generateTicket(Integer round) {
        return mockTicketFactory.generate(round);
    }
}
