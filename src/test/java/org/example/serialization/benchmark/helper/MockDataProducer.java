package org.example.serialization.benchmark.helper;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class MockDataProducer {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static Boolean generateGender(int round) {
        return round / 2 == 0;
    }

    public static int generateRandomBackPackWeightInKilogram() {
        return ThreadLocalRandom.current().nextInt(1_000, 5_000 + 1);
    }

    public static int generateRandomSuitcaseWeightInKilogram() {
        return ThreadLocalRandom.current().nextInt(10_000, 25_000 + 1);
    }

    public static String generateMockDepartureTime(int round) {
        return OffsetDateTime.now().plusMinutes(round).format(formatter);
    }

    public static String generateMockNrtArrivalTime(String departureTime) {
        return OffsetDateTime.parse(departureTime, formatter).plusHours(5).format(formatter);
    }

    public static String generateMockLaxArrivalTime(String departureTime) {
        return OffsetDateTime.parse(departureTime, formatter).plusHours(13).format(formatter);
    }

    public static String generateRandomNrtTicketPrice() {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(100.0, 1000.0)).toPlainString();
    }

    public static String generateRandomLaxTicketPrice() {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1000.0, 5000.0)).toPlainString();
    }

    private MockDataProducer() {}
}
