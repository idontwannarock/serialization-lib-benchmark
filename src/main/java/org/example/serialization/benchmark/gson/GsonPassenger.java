package org.example.serialization.benchmark.gson;

import lombok.Data;

@Data
public class GsonPassenger {

    private Integer id;
    private String firstName;
    private String lastName;
    private Boolean isMale;
    private GsonTicket ticket;
    private GsonBelonging[] belongings;
}
