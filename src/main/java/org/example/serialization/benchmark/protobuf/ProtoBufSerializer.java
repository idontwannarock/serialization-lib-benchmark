package org.example.serialization.benchmark.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoBufSerializer {

    public byte[] serialize(ProtoPassenger passenger) {
        return passenger.toByteArray();
    }

    public ProtoPassenger deserialize(byte[] serializedResult) throws InvalidProtocolBufferException {
        return ProtoPassenger.parseFrom(serializedResult);
    }
}
