package org.example.serialization.benchmark;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Value
@Builder
public class Ticket {

    Integer id;
    Transportation transportation;
    Currency currency;
    BigDecimal price;
    Location departure;
    OffsetDateTime departureTime;
    Location arrival;
    OffsetDateTime arrivalTime;

    public enum Transportation {
        AIRLINE, TRAIN
    }

    public enum Currency {
        USD, NTD
    }

    public enum Location {
        TPE, TSA, NRT, LAX
    }
}
