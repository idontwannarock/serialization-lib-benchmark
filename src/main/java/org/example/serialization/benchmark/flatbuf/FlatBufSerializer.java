package org.example.serialization.benchmark.flatbuf;

import java.nio.ByteBuffer;

public class FlatBufSerializer {

    public FlatPassenger deserialize(ByteBuffer passenger) {
        return FlatPassenger.getRootAsFlatPassenger(passenger);
    }
}
