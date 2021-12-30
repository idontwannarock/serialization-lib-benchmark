package com.example.serialization.benchmark.gson;

import lombok.Data;

@Data
public class GsonBelonging {

    private Integer id;
    private BelongingType type;
    private float weightInKilogram;

    public enum BelongingType {
        SUITCASE, BACKPACK
    }
}
