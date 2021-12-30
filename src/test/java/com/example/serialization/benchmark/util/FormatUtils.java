package com.example.serialization.benchmark.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FormatUtils {

    private final static NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private final static String THOUSAND_SEPARATOR_FORMAT = "%,f";

    private final static DecimalFormat totalFormatter = new DecimalFormat("0.000");
    private final static DecimalFormat averageFormatter = new DecimalFormat("0.000000000");

    private final static String RESULT_FORMAT = "| %-10s | %-9s | %-11s | %-14s | %-14s | %-14s | %-14s |";

    public static String formatWithThousandSeparator(int number) {
        return String.format(THOUSAND_SEPARATOR_FORMAT, number);
    }

    public static String formatTotalDuration(double timeInSeconds) {
        return totalFormatter.format(timeInSeconds);
    }

    public static String formatAverageDuration(double timeInSeconds) {
        return averageFormatter.format(timeInSeconds);
    }

    public static String formatResult(String serializer, String cost, String avgCost, String serCost, String avgSerCost, String desCost, String avgDesCost) {
        return String.format(RESULT_FORMAT, serializer, cost, avgCost, serCost, avgSerCost, desCost, avgDesCost);
    }

    private FormatUtils() {}
}
