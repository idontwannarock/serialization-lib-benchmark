package org.example.serialization.benchmark;

public interface MockFactory<Input, Result> {

    Result generate(Input input);
}
