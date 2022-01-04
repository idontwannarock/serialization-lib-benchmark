package org.example.serialization.benchmark.protobuf;

import org.example.serialization.benchmark.Belonging;
import org.example.serialization.benchmark.Passenger;
import org.example.serialization.benchmark.Ticket;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.serialization.benchmark.utils.FormatUtils.formatOffsetDateTime;
import static org.example.serialization.benchmark.utils.FormatUtils.parseTimeString;

public class ProtoPassengerMapper {

    ProtoPassengerMapper() {}

    ProtoPassenger map(Passenger passenger) {
        return ProtoPassenger.newBuilder()
                .setId(passenger.getId())
                .setFirstName(passenger.getFirstName())
                .setLastName(passenger.getLastName())
                .setIsMale(passenger.getIsMale())
                .addAllBelongings(mapBelongings(passenger.getBelongings()))
                .setTicket(mapTicket(passenger.getTicket()))
                .build();
    }

    private Iterable<ProtoBelonging> mapBelongings(List<Belonging> belongings) {
        List<ProtoBelonging> protoBelongings = new ArrayList<>();
        for (Belonging belonging : belongings) {
            protoBelongings.add(ProtoBelonging.newBuilder()
                    .setId(belonging.getId())
                    .setType(mapBelongingType(belonging.getType()))
                    .setWeightInKilogram(belonging.getWeightInKilogram())
                    .build());
        }
        return protoBelongings;
    }

    private ProtoBelonging.BelongingType mapBelongingType(Belonging.BelongingType type) {
        return type == Belonging.BelongingType.SUITCASE ? ProtoBelonging.BelongingType.SUITCASE : ProtoBelonging.BelongingType.BACKPACK;
    }

    private ProtoTicket mapTicket(Ticket ticket) {
        return ProtoTicket.newBuilder()
                .setId(ticket.getId())
                .setTransportation(mapTransportation(ticket.getTransportation()))
                .setCurrency(mapCurrency(ticket.getCurrency()))
                .setDeparture(mapDeparture(ticket.getDeparture()))
                .setDepartureTime(mapDepartureTime(ticket.getDepartureTime()))
                .setArrival(mapArrival(ticket.getArrival()))
                .setArrivalTime(mapArrivalTime(ticket.getArrivalTime()))
                .setPrice(mapPrice(ticket.getPrice()))
                .build();
    }

    private ProtoTicket.Transportation mapTransportation(Ticket.Transportation transportation) {
        return transportation == Ticket.Transportation.AIRLINE ? ProtoTicket.Transportation.AIRLINE : ProtoTicket.Transportation.TRAIN;
    }

    private ProtoTicket.Currency mapCurrency(Ticket.Currency currency) {
        return currency == Ticket.Currency.USD ? ProtoTicket.Currency.USD : ProtoTicket.Currency.NTD;
    }

    private ProtoTicket.Location mapDeparture(Ticket.Location departure) {
        return departure == Ticket.Location.TPE ? ProtoTicket.Location.TPE : ProtoTicket.Location.TSA;
    }

    private String mapDepartureTime(OffsetDateTime departureTime) {
        return formatOffsetDateTime(departureTime);
    }

    private ProtoTicket.Location mapArrival(Ticket.Location arrival) {
        return arrival == Ticket.Location.NRT ? ProtoTicket.Location.NRT : ProtoTicket.Location.LAX;
    }

    private String mapArrivalTime(OffsetDateTime arrivalTime) {
        return formatOffsetDateTime(arrivalTime);
    }

    private String mapPrice(BigDecimal price) {
        return price.toPlainString();
    }

    Passenger map(ProtoPassenger protoPassenger) {
        return Passenger.builder()
                .id(protoPassenger.getId())
                .firstName(protoPassenger.getFirstName())
                .lastName(protoPassenger.getLastName())
                .isMale(protoPassenger.getIsMale())
                .belongings(mapProtoBelongings(protoPassenger.getBelongingsList()))
                .ticket(mapTicket(protoPassenger.getTicket()))
                .build();
    }

    private List<Belonging> mapProtoBelongings(List<ProtoBelonging> protoBelongings) {
        List<Belonging> belongings = new ArrayList<>();
        for (ProtoBelonging protoBelonging : protoBelongings) {
            belongings.add(Belonging.builder()
                    .id(protoBelonging.getId())
                    .type(mapBelongingType(protoBelonging.getType()))
                    .weightInKilogram(protoBelonging.getWeightInKilogram())
                    .build());
        }
        return belongings;
    }

    private Belonging.BelongingType mapBelongingType(ProtoBelonging.BelongingType type) {
        return type == ProtoBelonging.BelongingType.SUITCASE ? Belonging.BelongingType.SUITCASE : Belonging.BelongingType.BACKPACK;
    }

    private Ticket mapTicket(ProtoTicket protoTicket) {
        return Ticket.builder()
                .id(protoTicket.getId())
                .transportation(mapTransportation(protoTicket.getTransportation()))
                .currency(mapCurrency(protoTicket.getCurrency()))
                .departure(mapDeparture(protoTicket.getDeparture()))
                .departureTime(mapDepartureTime(protoTicket.getDepartureTime()))
                .arrival(mapArrival(protoTicket.getArrival()))
                .arrivalTime(mapArrivalTime(protoTicket.getArrivalTime()))
                .price(mapPrice(protoTicket.getPrice()))
                .build();
    }

    private Ticket.Transportation mapTransportation(ProtoTicket.Transportation transportation) {
        return transportation == ProtoTicket.Transportation.AIRLINE ? Ticket.Transportation.AIRLINE : Ticket.Transportation.TRAIN;
    }

    private Ticket.Currency mapCurrency(ProtoTicket.Currency currency) {
        return currency == ProtoTicket.Currency.USD ? Ticket.Currency.USD : Ticket.Currency.NTD;
    }

    private Ticket.Location mapDeparture(ProtoTicket.Location departure) {
        return departure == ProtoTicket.Location.TPE ? Ticket.Location.TPE : Ticket.Location.TSA;
    }

    private OffsetDateTime mapDepartureTime(String departureTime) {
        return parseTimeString(departureTime);
    }

    private Ticket.Location mapArrival(ProtoTicket.Location arrival) {
        return arrival == ProtoTicket.Location.NRT ? Ticket.Location.NRT : Ticket.Location.LAX;
    }

    private OffsetDateTime mapArrivalTime(String arrivalTime) {
        return parseTimeString(arrivalTime);
    }

    private BigDecimal mapPrice(String price) {
        return BigDecimal.valueOf(Double.parseDouble(price));
    }
}
