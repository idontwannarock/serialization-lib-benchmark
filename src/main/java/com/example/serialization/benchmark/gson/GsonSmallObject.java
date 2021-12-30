package com.example.serialization.benchmark.gson;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class GsonSmallObject {

    private Integer id;
    private String firstName;
    private String lastName;
}
