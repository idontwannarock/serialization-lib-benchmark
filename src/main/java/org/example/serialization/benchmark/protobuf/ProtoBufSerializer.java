package org.example.serialization.benchmark.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import org.example.serialization.benchmark.Passenger;

public class ProtoBufSerializer {

    private static final ProtoPassengerMapper mapper = new ProtoPassengerMapper();

    public ProtoBufSerializer() {}

    public byte[] serialize(Passenger passenger) {
        return mapper.map(passenger).toByteArray();
    }

    public Passenger deserialize(byte[] serializedResult) throws InvalidProtocolBufferException {
        return mapper.map(ProtoPassenger.parseFrom(serializedResult));
    }
}
