package org.example.serialization.benchmark.flatbuf;

import com.google.flatbuffers.FlatBufferBuilder;
import org.example.serialization.benchmark.Belonging;
import org.example.serialization.benchmark.Passenger;
import org.example.serialization.benchmark.Ticket;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
import java.util.List;

import static org.example.serialization.benchmark.utils.FormatUtils.formatOffsetDateTime;

public class FlatBufSerializer {

    private static final FlatPassengerMapper mapper = new FlatPassengerMapper();

    public ByteBuffer serialize(Passenger passenger) {
        FlatBufferBuilder builder = new FlatBufferBuilder(0);
        // Everything except structs MUST be created before the start of the table they are referenced in.
        int id = passenger.getId();
        int firstName = builder.createString(passenger.getFirstName());
        int lastName = builder.createString(passenger.getLastName());
        boolean isMale = passenger.getIsMale();
        int belongings = generateMockBelongings(builder, passenger.getBelongings());
        int ticket = generateMockTicket(builder, passenger.getTicket());

        FlatPassenger.startFlatPassenger(builder);
        FlatPassenger.addId(builder, id);
        FlatPassenger.addFirstName(builder, firstName);
        FlatPassenger.addLastName(builder, lastName);
        FlatPassenger.addIsMale(builder, isMale);
        FlatPassenger.addBelongings(builder, belongings);
        FlatPassenger.addTicket(builder, ticket);
        int passengerOffset = FlatPassenger.endFlatPassenger(builder);
        builder.finish(passengerOffset);
        return builder.dataBuffer();
    }

    private int generateMockBelongings(FlatBufferBuilder builder, List<Belonging> belongings) {
        int[] flatBelongings = new int[belongings.size()];
        for (int index = 0; index < belongings.size(); index++) {
            Belonging belonging = belongings.get(index);
            int id = belonging.getId();
            int type = mapBelongingType(belonging.getType());
            float weightInKilogram = belonging.getWeightInKilogram();

            FlatBelonging.startFlatBelonging(builder);
            FlatBelonging.addId(builder, id);
            FlatBelonging.addType(builder, type);
            FlatBelonging.addWeightInKilogram(builder, weightInKilogram);
            int belongingOffset = FlatBelonging.endFlatBelonging(builder);
            flatBelongings[index] = belongingOffset;
        }
        return FlatPassenger.createBelongingsVector(builder, flatBelongings);
    }

    private int mapBelongingType(Belonging.BelongingType type) {
        return type == Belonging.BelongingType.SUITCASE ? FlatBelongingType.SUITCASE : FlatBelongingType.BACKPACK;
    }

    private int generateMockTicket(FlatBufferBuilder builder, Ticket ticket) {
        int id = ticket.getId();
        int transportation = mapTransportation(ticket.getTransportation());
        int departure = mapDeparture(ticket.getDeparture());
        int departureTime = mapDepartureTime(builder, ticket.getDepartureTime());
        int arrival = mapArrival(ticket.getArrival());
        int arrivalTime = mapArrivalTime(builder, ticket.getArrivalTime());
        int currency = mapCurrency(ticket.getCurrency());
        int price = mapPrice(builder, ticket.getPrice());

        FlatTicket.startFlatTicket(builder);
        FlatTicket.addId(builder, id);
        FlatTicket.addTransportation(builder, transportation);
        FlatTicket.addDeparture(builder, departure);
        FlatTicket.addDepartureTime(builder, departureTime);
        FlatTicket.addArrival(builder, arrival);
        FlatTicket.addArrivalTime(builder, arrivalTime);
        FlatTicket.addCurrency(builder, currency);
        FlatTicket.addPrice(builder, price);
        return FlatTicket.endFlatTicket(builder);
    }

    private int mapTransportation(Ticket.Transportation transportation) {
        return transportation == Ticket.Transportation.AIRLINE ? FlatTransportation.AIRLINE : FlatTransportation.TRAIN;
    }

    private int mapDeparture(Ticket.Location departure) {
        return departure == Ticket.Location.TSA ? FlatLocation.TSA : FlatLocation.TPE;
    }

    private int mapDepartureTime(FlatBufferBuilder builder, OffsetDateTime departureTime) {
        return convertOffsetDateTimeToOffset(builder, formatOffsetDateTime(departureTime));
    }

    private int convertOffsetDateTimeToOffset(FlatBufferBuilder builder, String timeString) {
        return builder.createString(timeString);
    }

    private int mapArrival(Ticket.Location arrival) {
        return arrival == Ticket.Location.NRT ? FlatLocation.NRT : FlatLocation.LAX;
    }

    private int mapArrivalTime(FlatBufferBuilder builder, OffsetDateTime arrivalTime) {
        return convertOffsetDateTimeToOffset(builder, formatOffsetDateTime(arrivalTime));
    }

    private int mapCurrency(Ticket.Currency currency) {
        return currency == Ticket.Currency.USD ? FlatCurrency.USD : FlatCurrency.NTD;
    }

    private int mapPrice(FlatBufferBuilder builder, BigDecimal price) {
        return builder.createString(price.toPlainString());
    }

    public Passenger deserialize(ByteBuffer byteBuffer) {
        return mapper.map(FlatPassenger.getRootAsFlatPassenger(byteBuffer));
    }
}
