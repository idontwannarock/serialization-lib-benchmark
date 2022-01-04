package org.example.serialization.benchmark.flatbuf;

import org.example.serialization.benchmark.Belonging;
import org.example.serialization.benchmark.Passenger;
import org.example.serialization.benchmark.Ticket;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.serialization.benchmark.utils.FormatUtils.parseTimeString;

public class FlatPassengerMapper {

    FlatPassengerMapper() {}

    Passenger map(FlatPassenger flatPassenger) {
        return Passenger.builder()
                .id(flatPassenger.id())
                .firstName(flatPassenger.firstName())
                .lastName(flatPassenger.lastName())
                .isMale(flatPassenger.isMale())
                .belongings(mapBelongings(flatPassenger))
                .ticket(mapTicket(flatPassenger.ticket()))
                .build();
    }

    private List<Belonging> mapBelongings(FlatPassenger flatPassenger) {
        List<Belonging> belongings = new ArrayList<>();
        for (int index = 0; index < flatPassenger.belongingsLength(); index++) {
            FlatBelonging flatBelonging = flatPassenger.belongings(index);
            belongings.add(Belonging.builder()
                    .id(flatBelonging.id())
                    .type(mapBelongingType(flatBelonging.type()))
                    .weightInKilogram(flatBelonging.weightInKilogram())
                    .build());
        }
        return belongings;
    }

    private Belonging.BelongingType mapBelongingType(int type) {
        return type == FlatBelongingType.SUITCASE ? Belonging.BelongingType.SUITCASE : Belonging.BelongingType.BACKPACK;
    }

    private Ticket mapTicket(FlatTicket ticket) {
        return Ticket.builder()
                .id(ticket.id())
                .transportation(mapTransportation(ticket.transportation()))
                .currency(mapCurrency(ticket.currency()))
                .departure(mapDeparture(ticket.departure()))
                .departureTime(mapDepartureTime(ticket.departureTime()))
                .arrival(mapArrival(ticket.arrival()))
                .arrivalTime(mapArrivalTime(ticket.arrivalTime()))
                .price(mapPrice(ticket.price()))
                .build();
    }

    private Ticket.Transportation mapTransportation(int transportation) {
        return transportation == FlatTransportation.AIRLINE ? Ticket.Transportation.AIRLINE : Ticket.Transportation.TRAIN;
    }

    private Ticket.Currency mapCurrency(int currency) {
        return currency == FlatCurrency.USD ? Ticket.Currency.USD : Ticket.Currency.NTD;
    }

    private Ticket.Location mapDeparture(int departure) {
        return departure == FlatLocation.TPE ? Ticket.Location.TPE : Ticket.Location.TSA;
    }

    private OffsetDateTime mapDepartureTime(String departureTime) {
        return parseTimeString(departureTime);
    }

    private Ticket.Location mapArrival(int arrival) {
        return arrival == FlatLocation.NRT ? Ticket.Location.NRT : Ticket.Location.LAX;
    }

    private OffsetDateTime mapArrivalTime(String arrivalTime) {
        return parseTimeString(arrivalTime);
    }

    private BigDecimal mapPrice(String price) {
        return BigDecimal.valueOf(Double.parseDouble(price));
    }
}
