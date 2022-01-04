package org.example.serialization.benchmark;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Passenger {

    Integer id;
    String firstName;
    String lastName;
    Boolean isMale;
    List<Belonging> belongings;
    Ticket ticket;
}
