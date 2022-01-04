package org.example.serialization.benchmark.gson;

import org.example.serialization.benchmark.Belonging;
import org.example.serialization.benchmark.Passenger;
import org.example.serialization.benchmark.Ticket;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.serialization.benchmark.utils.FormatUtils.formatOffsetDateTime;
import static org.example.serialization.benchmark.utils.FormatUtils.parseTimeString;

public class GsonPassengerMapper {

    GsonPassengerMapper() {}

    GsonPassenger map(Passenger passenger) {
        final GsonPassenger gsonPassenger = new GsonPassenger();
        gsonPassenger.setId(passenger.getId());
        gsonPassenger.setFirstName(passenger.getFirstName());
        gsonPassenger.setLastName(passenger.getLastName());
        gsonPassenger.setIsMale(passenger.getIsMale());
        gsonPassenger.setBelongings(convertBelongings(passenger.getBelongings()));
        gsonPassenger.setTicket(convertTicket(passenger.getTicket()));
        return gsonPassenger;
    }

    private GsonBelonging[] convertBelongings(List<Belonging> belongings) {
        GsonBelonging[] gsonBelongings = new GsonBelonging[belongings.size()];
        for (int index = 0; index < belongings.size(); index++) {
            Belonging belonging = belongings.get(index);
            GsonBelonging gsonBelonging = new GsonBelonging();
            gsonBelonging.setId(belonging.getId());
            gsonBelonging.setType(convertBelongingType(belonging.getType()));
            gsonBelonging.setWeightInKilogram(belonging.getWeightInKilogram());
            gsonBelongings[index] = gsonBelonging;
        }
        return gsonBelongings;
    }

    private GsonBelonging.BelongingType convertBelongingType(Belonging.BelongingType type) {
        return type == Belonging.BelongingType.SUITCASE ? GsonBelonging.BelongingType.SUITCASE : GsonBelonging.BelongingType.BACKPACK;
    }

    private GsonTicket convertTicket(Ticket ticket) {
        GsonTicket gsonTicket = new GsonTicket();
        gsonTicket.setId(ticket.getId());
        gsonTicket.setTransportation(convertTransportation(ticket.getTransportation()));
        gsonTicket.setCurrency(convertCurrency(ticket.getCurrency()));
        gsonTicket.setDeparture(convertDeparture(ticket.getDeparture()));
        gsonTicket.setDepartureTime(convertDepartureTime(ticket.getDepartureTime()));
        gsonTicket.setArrival(convertArrival(ticket.getArrival()));
        gsonTicket.setArrivalTime(convertArrivalTime(ticket.getArrivalTime()));
        gsonTicket.setPrice(convertPrice(ticket.getPrice()));
        return gsonTicket;
    }

    private GsonTicket.Transportation convertTransportation(Ticket.Transportation transportation) {
        return transportation == Ticket.Transportation.AIRLINE ? GsonTicket.Transportation.AIRLINE : GsonTicket.Transportation.TRAIN;
    }

    private GsonTicket.Currency convertCurrency(Ticket.Currency currency) {
        return currency == Ticket.Currency.USD ? GsonTicket.Currency.USD : GsonTicket.Currency.NTD;
    }

    private GsonTicket.Location convertDeparture(Ticket.Location departure) {
        return departure == Ticket.Location.TPE ? GsonTicket.Location.TPE : GsonTicket.Location.TSA;
    }

    private String convertDepartureTime(OffsetDateTime departureTime) {
        return formatOffsetDateTime(departureTime);
    }

    private GsonTicket.Location convertArrival(Ticket.Location arrival) {
        return arrival == Ticket.Location.NRT ? GsonTicket.Location.NRT : GsonTicket.Location.LAX;
    }

    private String convertArrivalTime(OffsetDateTime arrivalTime) {
        return formatOffsetDateTime(arrivalTime);
    }

    private String convertPrice(BigDecimal price) {
        return price.toPlainString();
    }

    Passenger map(GsonPassenger gsonPassenger) {
        return Passenger.builder()
                .id(gsonPassenger.getId())
                .firstName(gsonPassenger.getFirstName())
                .lastName(gsonPassenger.getLastName())
                .isMale(gsonPassenger.getIsMale())
                .belongings(convertBelongings(gsonPassenger.getBelongings()))
                .ticket(convertTicket(gsonPassenger.getTicket()))
                .build();
    }

    private List<Belonging> convertBelongings(GsonBelonging[] gsonBelongings) {
        List<Belonging> belongings = new ArrayList<>();
        for (GsonBelonging gsonBelonging : gsonBelongings) {
            belongings.add(Belonging.builder()
                    .id(gsonBelonging.getId())
                    .type(convertBelongingType(gsonBelonging.getType()))
                    .weightInKilogram(gsonBelonging.getWeightInKilogram())
                    .build());
        }
        return belongings;
    }

    private Belonging.BelongingType convertBelongingType(GsonBelonging.BelongingType type) {
        return type == GsonBelonging.BelongingType.SUITCASE ? Belonging.BelongingType.SUITCASE : Belonging.BelongingType.BACKPACK;
    }

    private Ticket convertTicket(GsonTicket gsonTicket) {
        return Ticket.builder()
                .id(gsonTicket.getId())
                .transportation(convertTransportation(gsonTicket.getTransportation()))
                .currency(convertCurrency(gsonTicket.getCurrency()))
                .departure(convertDeparture(gsonTicket.getDeparture()))
                .departureTime(convertDepartureTime(gsonTicket.getDepartureTime()))
                .arrival(convertArrival(gsonTicket.getArrival()))
                .arrivalTime(convertArrivalTime(gsonTicket.getArrivalTime()))
                .price(convertPrice(gsonTicket.getPrice()))
                .build();
    }

    private Ticket.Transportation convertTransportation(GsonTicket.Transportation transportation) {
        return transportation == GsonTicket.Transportation.AIRLINE ? Ticket.Transportation.AIRLINE : Ticket.Transportation.TRAIN;
    }

    private Ticket.Currency convertCurrency(GsonTicket.Currency currency) {
        return currency == GsonTicket.Currency.USD ? Ticket.Currency.USD : Ticket.Currency.NTD;
    }

    private Ticket.Location convertDeparture(GsonTicket.Location departure) {
        return departure == GsonTicket.Location.TPE ? Ticket.Location.TPE : Ticket.Location.TSA;
    }

    private OffsetDateTime convertDepartureTime(String departureTime) {
        return parseTimeString(departureTime);
    }

    private Ticket.Location convertArrival(GsonTicket.Location arrival) {
        return arrival == GsonTicket.Location.NRT ? Ticket.Location.NRT : Ticket.Location.LAX;
    }

    private OffsetDateTime convertArrivalTime(String arrivalTime) {
        return parseTimeString(arrivalTime);
    }

    private BigDecimal convertPrice(String price) {
        return BigDecimal.valueOf(Double.parseDouble(price));
    }
}
