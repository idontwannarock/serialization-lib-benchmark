package org.example.serialization.benchmark.util;

import java.text.DecimalFormat;

public class FormatUtils {

    private final static String THOUSAND_SEPARATOR_FORMAT = "%,d";

    private final static DecimalFormat totalFormatter = new DecimalFormat("0.000");
    private final static DecimalFormat averageFormatter = new DecimalFormat("0.000000000");

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

    private FormatUtils() {}
}
