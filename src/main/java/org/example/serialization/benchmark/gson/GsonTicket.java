package org.example.serialization.benchmark.gson;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class GsonTicket {

    private Integer id;
    private Transportation transportation;
    private Currency currency;
    private String price;
    private Location departure;
    private String departureTime;
    private Location arrival;
    private String arrivalTime;

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
