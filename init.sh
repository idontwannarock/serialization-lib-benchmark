!#/bin/bash

lib/protoc -I=src/main/proto --java_out=src/main/java src/main/proto/Passenger.proto