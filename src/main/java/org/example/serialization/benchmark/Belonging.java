package org.example.serialization.benchmark;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Belonging {

    Integer id;
    BelongingType type;
    Float weightInKilogram;

    public enum BelongingType {
        SUITCASE, BACKPACK
    }
}
