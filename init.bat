@echo off

.\lib\protoc.exe -I=src/main/proto --java_out=src\main\java src\main\proto\Passenger.proto

.\lib\flatc.exe --java -o src\main\java src\main\fbs\Passenger.fbs
