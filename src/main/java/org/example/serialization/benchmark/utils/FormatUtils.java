package org.example.serialization.benchmark.utils;

import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class FormatUtils {

    private final static String THOUSAND_SEPARATOR_FORMAT = "%,d";

    private final static DecimalFormat totalFormatter = new DecimalFormat("0.000");
    private final static DecimalFormat averageFormatter = new DecimalFormat("0.000000000");

    private final static DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static String formatWithThousandSeparator(long number) {
        return String.format(THOUSAND_SEPARATOR_FORMAT, number);
    }

    public static String formatTotalDuration(double timeInSeconds) {
        return totalFormatter.format(timeInSeconds);
    }

    public static String formatAverageDuration(double timeInSeconds) {
        return averageFormatter.format(timeInSeconds);
    }

    public static String formatResult(String format, String serializer, String cost, String avgCost, String serCost, String avgSerCost, String desCost, String avgDesCost, String maxUsedMemory) {
        return String.format(format, serializer, cost, avgCost, serCost, avgSerCost, desCost, avgDesCost, maxUsedMemory);
    }

    public static String formatOffsetDateTime(OffsetDateTime time) {
        return time.format(timeFormatter);
    }

    public static OffsetDateTime parseTimeString(String timeString) {
        return OffsetDateTime.parse(timeString, timeFormatter);
    }

    private FormatUtils() {}
}
