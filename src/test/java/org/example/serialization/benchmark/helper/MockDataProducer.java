package org.example.serialization.benchmark.helper;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class MockDataProducer {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static Boolean generateMockGender(int round) {
        return round / 2 == 0;
    }

    public static float generateRandomBackPackWeightInKilogram() {
        return (float) ThreadLocalRandom.current().nextDouble(1, 10);
    }

    public static float generateRandomSuitcaseWeightInKilogram() {
        return (float) ThreadLocalRandom.current().nextDouble(10, 25);
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

    public static String generateRandomNrtTicketPriceString() {
        return generateRandomNrtTicketPrice().toPlainString();
    }

    public static BigDecimal generateRandomNrtTicketPrice() {
        return generateRandomPriceInRange(100.0, 1000.0);
    }

    private static BigDecimal generateRandomPriceInRange(double origin, double bound) {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(origin, bound));
    }

    public static String generateRandomLaxTicketPriceString() {
        return generateRandomLaxTicketPrice().toPlainString();
    }

    public static BigDecimal generateRandomLaxTicketPrice() {
        return generateRandomPriceInRange(1000.0, 5000.0);
    }

    private MockDataProducer() {}
}
