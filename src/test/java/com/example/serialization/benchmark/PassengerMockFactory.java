package com.example.serialization.benchmark;

public interface PassengerMockFactory<T> {

    T generateMockPassenger(int round);
}
