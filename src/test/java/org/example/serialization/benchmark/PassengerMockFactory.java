package org.example.serialization.benchmark;

public interface PassengerMockFactory<T> {

    T generateMockPassenger(int round);
}
