package org.example.serialization.benchmark.gson;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class GsonTicket {

    private Integer id;
    private Transportation transportation;
    private Currency currency;
    private BigDecimal price;
    private Location departure;
    private LocalDateTime departureTime;
    private Location arrival;
    private LocalDateTime arrivalTime;

    public enum Location {
        TPE, TSA, NRT, LAX,
    }

    public enum Transportation {
        AIRLINE, TRAIN
    }

    public enum Currency {
        USD, NTD
    }
}
